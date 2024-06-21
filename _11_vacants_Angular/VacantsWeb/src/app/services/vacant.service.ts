import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VacantContent } from '../interfaces/vacant-interface/vacant.interface';
import { Observable } from 'rxjs';
import { Vacant } from '../interfaces/company-interface/company.interface';

@Injectable({
  providedIn: 'root'
})
export class VacantService {

  constructor(private http: HttpClient) { }

  private url:string = "http://localhost:8080/api/v1/vacant";

  getVacantList(page: number, size: number): Observable<VacantContent> 
  {
    return this.http.get<VacantContent>(`${this.url}?page=${page}&size=${size}`);
  }
  
  getOneVacant(id: string)
  {
    return this.http.get<Vacant>(`${this.url}/find/${id}`);
  }

  createVacant(vacant: Vacant)
  {
    return this.http.post<Vacant>(`${this.url}/add`, vacant);
  }

  updateVacant(vacant: Vacant, id: number)
  {
    return this.http.put<Vacant>(`${this.url}/update/${id}`, vacant);
  }

  deleteVacant(id: number)
  {
    return this.http.delete<void>(`${this.url}/delete/${id}`);
  }
}
