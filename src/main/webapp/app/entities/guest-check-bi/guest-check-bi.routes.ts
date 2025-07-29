import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import GuestCheckBIResolve from './route/guest-check-bi-routing-resolve.service';

const guestCheckBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/guest-check-bi.component').then(m => m.GuestCheckBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/guest-check-bi-detail.component').then(m => m.GuestCheckBIDetailComponent),
    resolve: {
      guestCheckBI: GuestCheckBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/guest-check-bi-update.component').then(m => m.GuestCheckBIUpdateComponent),
    resolve: {
      guestCheckBI: GuestCheckBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/guest-check-bi-update.component').then(m => m.GuestCheckBIUpdateComponent),
    resolve: {
      guestCheckBI: GuestCheckBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default guestCheckBIRoute;
