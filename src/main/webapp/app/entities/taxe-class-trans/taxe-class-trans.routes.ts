import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import TaxeClassTransResolve from './route/taxe-class-trans-routing-resolve.service';

const taxeClassTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/taxe-class-trans.component').then(m => m.TaxeClassTransComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/taxe-class-trans-detail.component').then(m => m.TaxeClassTransDetailComponent),
    resolve: {
      taxeClassTrans: TaxeClassTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/taxe-class-trans-update.component').then(m => m.TaxeClassTransUpdateComponent),
    resolve: {
      taxeClassTrans: TaxeClassTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/taxe-class-trans-update.component').then(m => m.TaxeClassTransUpdateComponent),
    resolve: {
      taxeClassTrans: TaxeClassTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default taxeClassTransRoute;
