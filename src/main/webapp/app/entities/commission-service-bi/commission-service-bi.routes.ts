import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CommissionServiceBIResolve from './route/commission-service-bi-routing-resolve.service';

const commissionServiceBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/commission-service-bi.component').then(m => m.CommissionServiceBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/commission-service-bi-detail.component').then(m => m.CommissionServiceBIDetailComponent),
    resolve: {
      commissionServiceBI: CommissionServiceBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/commission-service-bi-update.component').then(m => m.CommissionServiceBIUpdateComponent),
    resolve: {
      commissionServiceBI: CommissionServiceBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/commission-service-bi-update.component').then(m => m.CommissionServiceBIUpdateComponent),
    resolve: {
      commissionServiceBI: CommissionServiceBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default commissionServiceBIRoute;
