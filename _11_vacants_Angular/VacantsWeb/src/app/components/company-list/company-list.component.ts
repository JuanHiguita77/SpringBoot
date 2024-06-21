import { Component, OnInit, inject } from '@angular/core';
import { CompanyServiceService } from '../../services/company-service.service';
import { RouterModule } from '@angular/router';
import { Company, Content } from '../../interfaces/company-interface/company.interface';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './company-list.component.html',
  styleUrl: './company-list.component.css'
})
export default class CompanyListComponent implements OnInit
{
  private companyService = inject(CompanyServiceService);

  companies: Company[] = [];
  totalPages: number = 0;
  currentPage: number = 0;

  ngOnInit(): void {
    this.getCompanies(this.currentPage);
  }

  getCompanies(page: number): void {
    this.companyService.getCompanyList(page, 3).subscribe((content: Content) => {
      this.companies = content.content;
      this.totalPages = content.totalPages;
      this.currentPage = content.number + 1;
    });
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.getCompanies(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.getCompanies(this.currentPage - 1);
    }
  }

  deleteCompany(id:string): void{
    this.companyService.deleteCompany(id).subscribe(() =>
    {
      this.getCompanies(this.currentPage);
    });
  }
}
