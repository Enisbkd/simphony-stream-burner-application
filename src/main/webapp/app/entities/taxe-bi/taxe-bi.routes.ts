import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import TaxeBIResolve from './route/taxe-bi-routing-resolve.service';

const taxeBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/taxe-bi.component').then(m => m.TaxeBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/taxe-bi-detail.component').then(m => m.TaxeBIDetailComponent),
    resolve: {
      taxeBI: TaxeBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/taxe-bi-update.component').then(m => m.TaxeBIUpdateComponent),
    resolve: {
      taxeBI: TaxeBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/taxe-bi-update.component').then(m => m.TaxeBIUpdateComponent),
    resolve: {
      taxeBI: TaxeBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default taxeBIRoute;
