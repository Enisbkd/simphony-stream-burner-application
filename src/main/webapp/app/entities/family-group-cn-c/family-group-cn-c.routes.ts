import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import FamilyGroupCnCResolve from './route/family-group-cn-c-routing-resolve.service';

const familyGroupCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/family-group-cn-c.component').then(m => m.FamilyGroupCnCComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/family-group-cn-c-detail.component').then(m => m.FamilyGroupCnCDetailComponent),
    resolve: {
      familyGroupCnC: FamilyGroupCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/family-group-cn-c-update.component').then(m => m.FamilyGroupCnCUpdateComponent),
    resolve: {
      familyGroupCnC: FamilyGroupCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/family-group-cn-c-update.component').then(m => m.FamilyGroupCnCUpdateComponent),
    resolve: {
      familyGroupCnC: FamilyGroupCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default familyGroupCnCRoute;
