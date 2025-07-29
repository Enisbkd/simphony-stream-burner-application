import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PointDeVenteCnCResolve from './route/point-de-vente-cn-c-routing-resolve.service';

const pointDeVenteCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/point-de-vente-cn-c.component').then(m => m.PointDeVenteCnCComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/point-de-vente-cn-c-detail.component').then(m => m.PointDeVenteCnCDetailComponent),
    resolve: {
      pointDeVenteCnC: PointDeVenteCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/point-de-vente-cn-c-update.component').then(m => m.PointDeVenteCnCUpdateComponent),
    resolve: {
      pointDeVenteCnC: PointDeVenteCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/point-de-vente-cn-c-update.component').then(m => m.PointDeVenteCnCUpdateComponent),
    resolve: {
      pointDeVenteCnC: PointDeVenteCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pointDeVenteCnCRoute;
