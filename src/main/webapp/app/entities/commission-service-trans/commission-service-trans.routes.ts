import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CommissionServiceTransResolve from './route/commission-service-trans-routing-resolve.service';

const commissionServiceTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/commission-service-trans.component').then(m => m.CommissionServiceTransComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/commission-service-trans-detail.component').then(m => m.CommissionServiceTransDetailComponent),
    resolve: {
      commissionServiceTrans: CommissionServiceTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/commission-service-trans-update.component').then(m => m.CommissionServiceTransUpdateComponent),
    resolve: {
      commissionServiceTrans: CommissionServiceTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/commission-service-trans-update.component').then(m => m.CommissionServiceTransUpdateComponent),
    resolve: {
      commissionServiceTrans: CommissionServiceTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default commissionServiceTransRoute;
