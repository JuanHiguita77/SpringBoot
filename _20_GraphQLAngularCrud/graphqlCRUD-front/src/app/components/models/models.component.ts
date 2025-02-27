import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Apollo, QueryRef } from 'apollo-angular';
import { Subscription } from 'rxjs';
import { DELETE_MODEL } from 'src/app/graphql/mutations-graphql';
import { GET_MODELS } from 'src/app/graphql/queries.graphql';

@Component({
  selector: 'app-models',
  templateUrl: './models.component.html',
  styleUrls: ['./models.component.scss']
})
export class ModelsComponent implements OnInit {

  loading: boolean;
  models: any;
  modelsQuerys: QueryRef<any>;

  private querySubscription: Subscription;

  constructor(
    private apollo: Apollo,
    private activatedRoute: ActivatedRoute
  ) { }

  refresh(): void {
    this.modelsQuerys.refetch();
  }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['brandId'];
    this.loadModels(id);
  }

  ngOnDestroy(): void {
    this.querySubscription.unsubscribe();
  }

  onDelete(id: number): void{
    this.apollo.mutate({
      mutation: DELETE_MODEL,
      variables: {
        id: id
      }
    }).subscribe({
      next: () => {
        this.refresh();
      },
      error: (error) => {
        alert(error)
      }
    })
  }

  loadModels(id: number): void{
    const variables = { 
      page: 1, 
      size: 10,
      brandId: id
    };

    this.modelsQuerys = this.apollo.watchQuery<any>({
      query: GET_MODELS,
      variables: variables
    });

    this.querySubscription = this.modelsQuerys
    .valueChanges.subscribe(({ data, loading }) => {
      this.loading = loading;
      
      this.models = data.getModelsByBrandId.content;

      this.refresh();
      
    }),
    error => {
      alert(error);
    }
  }
}
