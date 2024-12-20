import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Apollo, QueryRef } from 'apollo-angular';
import { UPDATE_MODEL } from 'src/app/graphql/mutations-graphql';

@Component({
  selector: 'app-update-model',
  templateUrl: './update-model.component.html',
  styleUrls: ['./update-model.component.scss']
})
export class UpdateModelComponent implements OnInit{

  name: string = '';
  brandId: number = 0;
  loading: string = '';

  constructor(
    private apollo: Apollo,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.name = this.activatedRoute.snapshot.params['name'];
    this.brandId = Number(this.activatedRoute.snapshot.params['brandId']);
  }

  onUpdate(): void {
    this.apollo.mutate({
      mutation: UPDATE_MODEL,
      variables: {
        id: this.activatedRoute.snapshot.params['modelId'],
        request: {
          name: this.name,
          brandId: this.brandId
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
