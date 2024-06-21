import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Company, Content } from '../interfaces/company-interface/company.interface';
  
@Injectable({
  providedIn: 'root'
})
export class CompanyServiceService {

  constructor(private http: HttpClient) { }

  private url:string = "http://localhost:8080/api/v1/company";

  getCompanyList(page: number, size: number): Observable<Content> 
  {
    return this.http.get<Content>(`${this.url}?page=${page}&size=${size}`);
  }
  
  getOneCompany(id: string)
  {
    return this.http.get<Company>(`${this.url}/find/${id}`);
  }

  createCompany(company: Company)
  {
    return this.http.post<Company>(`${this.url}/add`, company);
  }

  updateCompany(company: Company, id: string)
  {
    return this.http.put<Company>(`${this.url}/${id}`, company);
  }

  deleteCompany(id: string)
  {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
