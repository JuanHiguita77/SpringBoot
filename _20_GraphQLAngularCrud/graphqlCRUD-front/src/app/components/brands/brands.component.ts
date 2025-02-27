import { Component, OnDestroy, OnInit } from '@angular/core';
import { Apollo, QueryRef } from 'apollo-angular';
import { Subscription } from 'rxjs';
import { DELETE_BRAND } from 'src/app/graphql/mutations-graphql';
import { GET_BRANDS } from 'src/app/graphql/queries.graphql';

@Component({
  selector: 'app-brands',
  templateUrl: './brands.component.html',
  styleUrls: ['./brands.component.scss']
})
export class BrandsComponent implements OnInit, OnDestroy {

  loading: boolean;
  brands: any;

  private querySubscription: Subscription;

  brandsQuery: QueryRef<any>;

  constructor(
    private apollo: Apollo
  ) { }

  ngOnInit(): void {
    this.loadBrands();
  }

  loadBrands(): void{

    const variables = { 
      page: 1, 
      size: 15 
    };

    this.brandsQuery = this.apollo.watchQuery<any>({
      query: GET_BRANDS,
      variables: variables
    })
    this.querySubscription = this.brandsQuery.valueChanges.subscribe(({ data, loading }) => {
      this.loading = loading;

      this.brands = data.getAllBrands.content;

      this.refresh();
    });
  }

  refresh(): void {
    this.brandsQuery.refetch();
  }

  ngOnDestroy(): void {
    this.querySubscription.unsubscribe();
  }

  onDelete(id: number): void {
    this.apollo.mutate({
      mutation: DELETE_BRAND,
      variables: {
        id: id
      }
    }).subscribe({
      next: () => {
        this.refresh();
      },
      error: (error) => {
        alert(error);
      }
    });
  }
}
