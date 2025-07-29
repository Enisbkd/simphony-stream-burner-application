import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import HttpCallAuditResolve from './route/http-call-audit-routing-resolve.service';

const httpCallAuditRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/http-call-audit.component').then(m => m.HttpCallAuditComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/http-call-audit-detail.component').then(m => m.HttpCallAuditDetailComponent),
    resolve: {
      httpCallAudit: HttpCallAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/http-call-audit-update.component').then(m => m.HttpCallAuditUpdateComponent),
    resolve: {
      httpCallAudit: HttpCallAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/http-call-audit-update.component').then(m => m.HttpCallAuditUpdateComponent),
    resolve: {
      httpCallAudit: HttpCallAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default httpCallAuditRoute;
