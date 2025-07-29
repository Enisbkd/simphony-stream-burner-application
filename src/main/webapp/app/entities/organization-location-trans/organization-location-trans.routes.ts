import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import OrganizationLocationTransResolve from './route/organization-location-trans-routing-resolve.service';

const organizationLocationTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/organization-location-trans.component').then(m => m.OrganizationLocationTransComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/organization-location-trans-detail.component').then(m => m.OrganizationLocationTransDetailComponent),
    resolve: {
      organizationLocationTrans: OrganizationLocationTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/organization-location-trans-update.component').then(m => m.OrganizationLocationTransUpdateComponent),
    resolve: {
      organizationLocationTrans: OrganizationLocationTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/organization-location-trans-update.component').then(m => m.OrganizationLocationTransUpdateComponent),
    resolve: {
      organizationLocationTrans: OrganizationLocationTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default organizationLocationTransRoute;
