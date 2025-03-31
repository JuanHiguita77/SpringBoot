import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Apollo } from 'apollo-angular';
import { CREATE_MODEL } from 'src/app/graphql/mutations-graphql';

@Component({
  selector: 'app-create-model',
  templateUrl: './create-model.component.html',
  styleUrls: ['./create-model.component.scss']
})
export class CreateModelComponent {
  name = '';
  brandId = 0;

  constructor(
    private apollo: Apollo,
    private router: Router
  ){}

  onCreate(): void {
    this.apollo.mutate({
      mutation: CREATE_MODEL,
      variables: {
        modelDTO: {
          name: this.name,
          brandId: this.brandId
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
