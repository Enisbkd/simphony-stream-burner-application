import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import LocationCnCResolve from './route/location-cn-c-routing-resolve.service';

const locationCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/location-cn-c.component').then(m => m.LocationCnCComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/location-cn-c-detail.component').then(m => m.LocationCnCDetailComponent),
    resolve: {
      locationCnC: LocationCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/location-cn-c-update.component').then(m => m.LocationCnCUpdateComponent),
    resolve: {
      locationCnC: LocationCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/location-cn-c-update.component').then(m => m.LocationCnCUpdateComponent),
    resolve: {
      locationCnC: LocationCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default locationCnCRoute;
