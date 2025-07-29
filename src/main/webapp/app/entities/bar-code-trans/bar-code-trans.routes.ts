import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import BarCodeTransResolve from './route/bar-code-trans-routing-resolve.service';

const barCodeTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/bar-code-trans.component').then(m => m.BarCodeTransComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/bar-code-trans-detail.component').then(m => m.BarCodeTransDetailComponent),
    resolve: {
      barCodeTrans: BarCodeTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/bar-code-trans-update.component').then(m => m.BarCodeTransUpdateComponent),
    resolve: {
      barCodeTrans: BarCodeTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/bar-code-trans-update.component').then(m => m.BarCodeTransUpdateComponent),
    resolve: {
      barCodeTrans: BarCodeTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default barCodeTransRoute;
