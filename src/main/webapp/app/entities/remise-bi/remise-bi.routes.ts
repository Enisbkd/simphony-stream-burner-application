import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import RemiseBIResolve from './route/remise-bi-routing-resolve.service';

const remiseBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/remise-bi.component').then(m => m.RemiseBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/remise-bi-detail.component').then(m => m.RemiseBIDetailComponent),
    resolve: {
      remiseBI: RemiseBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/remise-bi-update.component').then(m => m.RemiseBIUpdateComponent),
    resolve: {
      remiseBI: RemiseBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/remise-bi-update.component').then(m => m.RemiseBIUpdateComponent),
    resolve: {
      remiseBI: RemiseBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default remiseBIRoute;
