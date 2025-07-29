import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import HierarchieCnCResolve from './route/hierarchie-cn-c-routing-resolve.service';

const hierarchieCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/hierarchie-cn-c.component').then(m => m.HierarchieCnCComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/hierarchie-cn-c-detail.component').then(m => m.HierarchieCnCDetailComponent),
    resolve: {
      hierarchieCnC: HierarchieCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/hierarchie-cn-c-update.component').then(m => m.HierarchieCnCUpdateComponent),
    resolve: {
      hierarchieCnC: HierarchieCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/hierarchie-cn-c-update.component').then(m => m.HierarchieCnCUpdateComponent),
    resolve: {
      hierarchieCnC: HierarchieCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default hierarchieCnCRoute;
