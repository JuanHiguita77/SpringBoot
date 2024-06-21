import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { VacantService } from '../../services/vacant.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Vacant } from '../../interfaces/company-interface/company.interface';

@Component({
  selector: 'app-vacant-form',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './vacant-form.component.html',
  styleUrl: './vacant-form.component.css'
})
export default class VacantFormComponent {
  private formBuilder = inject(FormBuilder);
  private vacantService = inject(VacantService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form?: FormGroup;
  vacant?: Vacant;
  errors: string[] = [];

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    
    if(id)
    {
      this.vacantService.getOneVacant(id).subscribe(vacant =>
      {
        this.vacant = vacant;
        
        this.form = this.formBuilder.group({
          title:[vacant.title, [Validators.required]],
          description:[vacant.description, [Validators.required]],
          companyId:[vacant.companyId, [Validators.required]]
        });
      });
    }
    else
    {
      this.form = this.formBuilder.group({
        title:['', [Validators.required]],
        description:['', [Validators.required]],
        companyId:['', [Validators.required]]
      });
    }
  }

  save()
  {
    if(this.form?.invalid)
    {
      return;
    }

    const vacantFormData = this.form!.value;
    
    if(this.vacant)
    {
      this.vacantService.updateVacant(vacantFormData, this.vacant.id).subscribe({
        next: () => 
        {
          this.errors = [];
          this.router.navigate(['/vacants']);        
        }, error: response => 
        {
          this.errors = response.error.errors;
        }
      });
      return;
    }

    this.vacantService.createVacant(vacantFormData).subscribe(
      {
        next: () => 
        {
          this.errors = [];
          this.router.navigate(['/vacants']);        
        }, error: response => 
        {
          this.errors = response.error.errors;
        }
      });
  }
}
