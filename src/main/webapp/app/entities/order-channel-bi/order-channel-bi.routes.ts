import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OrderChannelBIResolve from './route/order-channel-bi-routing-resolve.service';

const orderChannelBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/order-channel-bi.component').then(m => m.OrderChannelBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/order-channel-bi-detail.component').then(m => m.OrderChannelBIDetailComponent),
    resolve: {
      orderChannelBI: OrderChannelBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/order-channel-bi-update.component').then(m => m.OrderChannelBIUpdateComponent),
    resolve: {
      orderChannelBI: OrderChannelBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/order-channel-bi-update.component').then(m => m.OrderChannelBIUpdateComponent),
    resolve: {
      orderChannelBI: OrderChannelBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default orderChannelBIRoute;
