import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CheckTransResolve from './route/check-trans-routing-resolve.service';

const checkTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/check-trans.component').then(m => m.CheckTransComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/check-trans-detail.component').then(m => m.CheckTransDetailComponent),
    resolve: {
      checkTrans: CheckTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/check-trans-update.component').then(m => m.CheckTransUpdateComponent),
    resolve: {
      checkTrans: CheckTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/check-trans-update.component').then(m => m.CheckTransUpdateComponent),
    resolve: {
      checkTrans: CheckTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default checkTransRoute;
