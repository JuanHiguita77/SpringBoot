import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CompanyServiceService } from '../../services/company-service.service';
import { Company } from '../../interfaces/company-interface/company.interface';

@Component({
  selector: 'app-company-form',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './company-form.component.html',
  styleUrl: './company-form.component.css'
})
export default class CompanyFormComponent implements OnInit{
  private formBuilder = inject(FormBuilder);
  private companyService = inject(CompanyServiceService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form?: FormGroup;
  company?: Company;
  errors: string[] = [];

  ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
      
      if(id)
      {
        this.companyService.getOneCompany(id).subscribe(company =>
        {
          this.company = company;
          
          this.form = this.formBuilder.group({
            name:[company.name, [Validators.required]],
            location:[company.location, [Validators.required]],
            contact:[company.contact, [Validators.required]]
          });
        });
      }
      else
      {
        this.form = this.formBuilder.group({
          name:['', [Validators.required]],
          location:['', [Validators.required]],
          contact:['', [Validators.required]]
        });
      }
  }

  save()
  {
    if(this.form?.invalid)
    {
      return;
    }

    const companyFormData = this.form!.value;
    
    if(this.company)
    {
      this.companyService.updateCompany(companyFormData, this.company.id).subscribe({
        next: () => 
        {
          this.errors = [];
          this.router.navigate(['/']);        
        }, error: response => 
        {
          this.errors = response.error.errors;
        }
      });
      return;
    }

    this.companyService.createCompany(companyFormData).subscribe(
      {
        next: () => 
        {
          this.errors = [];
          this.router.navigate(['/']);        
        }, error: response => 
        {
          this.errors = response.error.errors;
        }
      });
  }
}
