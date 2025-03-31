import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Apollo } from 'apollo-angular';
import { UPDATE_BRAND } from 'src/app/graphql/mutations-graphql';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.scss']
})
export class UpdateComponent implements OnInit{

  name: string = '';
  countries: string[] = ['GER', 'ENG', 'ES', 'JP', 'US', 'FR', 'KR'];
  selectedCountry: string = '';

  constructor(
    private apollo: Apollo,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.name = this.activatedRoute.snapshot.params['name'];
    this.selectedCountry = this.activatedRoute.snapshot.params['country'];
  }

  onUpdate(): void {
    this.apollo.mutate({
      mutation: UPDATE_BRAND,
      variables: {
        id: this.activatedRoute.snapshot.params['brandId'],
        request: {
          name: this.name,
          country: this.selectedCountry
        }
      }
    }).subscribe({
      next: () => {
        alert('Updated!');
        this.router.navigate(['/']);
      },
      error: (error) => {
        alert(error);
      }
    });
  }
}
