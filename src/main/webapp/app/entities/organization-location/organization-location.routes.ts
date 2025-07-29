import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OrganizationLocationResolve from './route/organization-location-routing-resolve.service';

const organizationLocationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/organization-location.component').then(m => m.OrganizationLocationComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/organization-location-detail.component').then(m => m.OrganizationLocationDetailComponent),
    resolve: {
      organizationLocation: OrganizationLocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/organization-location-update.component').then(m => m.OrganizationLocationUpdateComponent),
    resolve: {
      organizationLocation: OrganizationLocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/organization-location-update.component').then(m => m.OrganizationLocationUpdateComponent),
    resolve: {
      organizationLocation: OrganizationLocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default organizationLocationRoute;
