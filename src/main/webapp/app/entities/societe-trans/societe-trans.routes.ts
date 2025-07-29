import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SocieteTransResolve from './route/societe-trans-routing-resolve.service';

const societeTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/societe-trans.component').then(m => m.SocieteTransComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/societe-trans-detail.component').then(m => m.SocieteTransDetailComponent),
    resolve: {
      societeTrans: SocieteTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/societe-trans-update.component').then(m => m.SocieteTransUpdateComponent),
    resolve: {
      societeTrans: SocieteTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/societe-trans-update.component').then(m => m.SocieteTransUpdateComponent),
    resolve: {
      societeTrans: SocieteTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default societeTransRoute;
