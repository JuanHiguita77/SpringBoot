import { Component, OnInit, inject } from '@angular/core';
import { VacantService } from '../../services/vacant.service';
import { VacantContent } from '../../interfaces/vacant-interface/vacant.interface';
import { Vacant } from '../../interfaces/company-interface/company.interface';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-vacant-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './vacant-list.component.html',
  styleUrl: './vacant-list.component.css'
})
export default class VacantListComponent implements OnInit
{
  private vacantService = inject(VacantService);

  totalPages:number = 0;
  currentPage: number = 0;
  vacants: Vacant[] = [];


  ngOnInit(): void {
    this.getVacants(this.currentPage);
  }

  getVacants(page: number): void {
    this.vacantService.getVacantList(page, 3).subscribe((content: VacantContent) => {
      this.vacants = content.content;
      this.totalPages = content.totalPages;
      this.currentPage = content.number + 1;
    });
  }

  nextPage(): void 
  {
    if (this.currentPage < this.totalPages) 
    {
      this.getVacants(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.getVacants(this.currentPage - 1);
    }
  }

  deleteVacant(id:number): void{
    this.vacantService.deleteVacant(id).subscribe(() =>
    {
      this.getVacants(this.currentPage);
    });
  }
}
