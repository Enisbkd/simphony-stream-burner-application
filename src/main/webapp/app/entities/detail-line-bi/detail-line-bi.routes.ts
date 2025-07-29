import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DetailLineBIResolve from './route/detail-line-bi-routing-resolve.service';

const detailLineBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/detail-line-bi.component').then(m => m.DetailLineBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/detail-line-bi-detail.component').then(m => m.DetailLineBIDetailComponent),
    resolve: {
      detailLineBI: DetailLineBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/detail-line-bi-update.component').then(m => m.DetailLineBIUpdateComponent),
    resolve: {
      detailLineBI: DetailLineBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/detail-line-bi-update.component').then(m => m.DetailLineBIUpdateComponent),
    resolve: {
      detailLineBI: DetailLineBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default detailLineBIRoute;
