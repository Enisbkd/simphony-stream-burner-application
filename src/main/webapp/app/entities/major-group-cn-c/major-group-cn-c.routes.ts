import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MajorGroupCnCResolve from './route/major-group-cn-c-routing-resolve.service';

const majorGroupCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/major-group-cn-c.component').then(m => m.MajorGroupCnCComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/major-group-cn-c-detail.component').then(m => m.MajorGroupCnCDetailComponent),
    resolve: {
      majorGroupCnC: MajorGroupCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/major-group-cn-c-update.component').then(m => m.MajorGroupCnCUpdateComponent),
    resolve: {
      majorGroupCnC: MajorGroupCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/major-group-cn-c-update.component').then(m => m.MajorGroupCnCUpdateComponent),
    resolve: {
      majorGroupCnC: MajorGroupCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default majorGroupCnCRoute;
