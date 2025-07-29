import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OrderChannelResolve from './route/order-channel-routing-resolve.service';

const orderChannelRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/order-channel.component').then(m => m.OrderChannelComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/order-channel-detail.component').then(m => m.OrderChannelDetailComponent),
    resolve: {
      orderChannel: OrderChannelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/order-channel-update.component').then(m => m.OrderChannelUpdateComponent),
    resolve: {
      orderChannel: OrderChannelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/order-channel-update.component').then(m => m.OrderChannelUpdateComponent),
    resolve: {
      orderChannel: OrderChannelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default orderChannelRoute;
