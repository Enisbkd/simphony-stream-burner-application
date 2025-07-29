import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import OrderTypeBIResolve from './route/order-type-bi-routing-resolve.service';

const orderTypeBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/order-type-bi.component').then(m => m.OrderTypeBIComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/order-type-bi-detail.component').then(m => m.OrderTypeBIDetailComponent),
    resolve: {
      orderTypeBI: OrderTypeBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/order-type-bi-update.component').then(m => m.OrderTypeBIUpdateComponent),
    resolve: {
      orderTypeBI: OrderTypeBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/order-type-bi-update.component').then(m => m.OrderTypeBIUpdateComponent),
    resolve: {
      orderTypeBI: OrderTypeBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default orderTypeBIRoute;
