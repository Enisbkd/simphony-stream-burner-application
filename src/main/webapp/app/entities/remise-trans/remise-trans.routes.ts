import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import RemiseTransResolve from './route/remise-trans-routing-resolve.service';

const remiseTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/remise-trans.component').then(m => m.RemiseTransComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/remise-trans-detail.component').then(m => m.RemiseTransDetailComponent),
    resolve: {
      remiseTrans: RemiseTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/remise-trans-update.component').then(m => m.RemiseTransUpdateComponent),
    resolve: {
      remiseTrans: RemiseTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/remise-trans-update.component').then(m => m.RemiseTransUpdateComponent),
    resolve: {
      remiseTrans: RemiseTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default remiseTransRoute;
