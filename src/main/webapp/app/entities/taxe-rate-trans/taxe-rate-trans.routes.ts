import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import TaxeRateTransResolve from './route/taxe-rate-trans-routing-resolve.service';

const taxeRateTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/taxe-rate-trans.component').then(m => m.TaxeRateTransComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/taxe-rate-trans-detail.component').then(m => m.TaxeRateTransDetailComponent),
    resolve: {
      taxeRateTrans: TaxeRateTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/taxe-rate-trans-update.component').then(m => m.TaxeRateTransUpdateComponent),
    resolve: {
      taxeRateTrans: TaxeRateTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/taxe-rate-trans-update.component').then(m => m.TaxeRateTransUpdateComponent),
    resolve: {
      taxeRateTrans: TaxeRateTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default taxeRateTransRoute;
