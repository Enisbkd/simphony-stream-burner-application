import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import PointDeVenteTransResolve from './route/point-de-vente-trans-routing-resolve.service';

const pointDeVenteTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/point-de-vente-trans.component').then(m => m.PointDeVenteTransComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/point-de-vente-trans-detail.component').then(m => m.PointDeVenteTransDetailComponent),
    resolve: {
      pointDeVenteTrans: PointDeVenteTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/point-de-vente-trans-update.component').then(m => m.PointDeVenteTransUpdateComponent),
    resolve: {
      pointDeVenteTrans: PointDeVenteTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/point-de-vente-trans-update.component').then(m => m.PointDeVenteTransUpdateComponent),
    resolve: {
      pointDeVenteTrans: PointDeVenteTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pointDeVenteTransRoute;
