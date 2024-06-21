import { Routes } from '@angular/router';

export const routes: Routes = [
    {
      path: "", 
      loadComponent: () => import('./components/company-list/company-list.component') 
    },
    {
      path: "newCompany",
      loadComponent: () => import('./components/company-form/company-form.component')
    },
    {
      path: ":id/edit",
      loadComponent: () => import('./components/company-form/company-form.component')
    },
    {
      path: "newVacant",
      loadComponent: () => import('./components/vacant-form/vacant-form.component')
    },    
    {
      path: "vacants",
      loadComponent: () => import('./components/vacant-list/vacant-list.component')
    },
    {
      path: ":id/editVacant",
      loadComponent: () => import('./components/vacant-form/vacant-form.component')
    },
];
