import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GraphQLModule } from './graphql.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrandsComponent } from './components/brands/brands.component';
import { MenuComponent } from './components/menu/menu.component';
import { ModelsComponent } from './components/models/models.component';
import { CreateComponent } from './components/brands/create-brand/create.component';
import { UpdateComponent } from './components/brands/update-brand/update.component';
import { CreateModelComponent } from './components/models/create-model/create-model.component';
import { UpdateModelComponent } from './components/models/update-model/update-model.component';


@NgModule({
  declarations: [
    AppComponent,
    BrandsComponent,
    MenuComponent,
    ModelsComponent,
    CreateComponent,
    UpdateComponent,
    CreateModelComponent,
    UpdateModelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GraphQLModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
