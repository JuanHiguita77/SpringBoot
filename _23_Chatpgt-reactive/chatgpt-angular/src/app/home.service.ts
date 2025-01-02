import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private backendUrl = "http://localhost:8080/chat";

  constructor(private httpClient: HttpClient) { }

  public getContent(prompt: string): Observable<any>{
      return this.httpClient.post<any>(this.backendUrl, prompt);
  }
}
