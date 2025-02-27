import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrandsComponent } from './components/brands/brands.component';
import { ModelsComponent } from './components/models/models.component';
import { CreateComponent } from './components/brands/create-brand/create.component';
import { UpdateComponent } from './components/brands/update-brand/update.component';
import { CreateModelComponent } from './components/models/create-model/create-model.component';
import { UpdateModelComponent } from './components/models/update-model/update-model.component';

const routes: Routes = [
  {
    path: '',
    component: BrandsComponent
  },
  {
    path: 'models/:brandId',
    component: ModelsComponent
  },
  {
    path: 'create-brand',
    component: CreateComponent
  },
  {
    path: 'update/:brandId/:name/:country',
    component: UpdateComponent
  },
  {
    path: 'create-model',
    component: CreateModelComponent
  },
  {
    path: 'update-model/:modelId/:name/:brandId',
    component: UpdateModelComponent
  },
  {
    path: '**', 
    redirectTo: '', 
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
