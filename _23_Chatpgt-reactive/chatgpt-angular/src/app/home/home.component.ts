import { Component, OnInit } from '@angular/core';
import { HomeService } from '../home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  prompt: string = '';
  content: string = '';

  constructor(
    private homeService: HomeService
  ) { }

  ngOnInit(): void { }

  ngOnSubmit(): void {
    this.content = "Waiting response...";
    
    this.homeService.getContent(this.prompt).subscribe(
      (response) => {
        this.content = response.content;
      },
      error => {
        alert(error.message);
      }
    );
  }

  resetPrompt(): void {
    this.prompt = '';
  }

  resetContent(): void {
    this.content = '';
  }
}
