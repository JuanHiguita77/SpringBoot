import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Apollo } from 'apollo-angular';
import { CREATE_BRAND } from 'src/app/graphql/mutations-graphql';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit{

  name: string = '';
  countries: string[] = ['GER', 'ENG', 'ES', 'JP', 'US', 'FR', 'KR'];
  selectedCountry: string = '';
  
  constructor(
    private apollo: Apollo,
    private router: Router
  ){}

  ngOnInit(): void {
    console.log("onINit");
  }

  onCreate(): void {  
    this.apollo.mutate({
      mutation: CREATE_BRAND,
      variables: {
        brandDTO: {
          name: this.name,
          country: this.selectedCountry
        }
      }
    }).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        alert(error);
      }
    });
  }
}
