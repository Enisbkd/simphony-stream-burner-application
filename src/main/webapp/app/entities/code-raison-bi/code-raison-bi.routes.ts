import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CodeRaisonBIResolve from './route/code-raison-bi-routing-resolve.service';

const codeRaisonBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/code-raison-bi.component').then(m => m.CodeRaisonBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/code-raison-bi-detail.component').then(m => m.CodeRaisonBIDetailComponent),
    resolve: {
      codeRaisonBI: CodeRaisonBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/code-raison-bi-update.component').then(m => m.CodeRaisonBIUpdateComponent),
    resolve: {
      codeRaisonBI: CodeRaisonBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/code-raison-bi-update.component').then(m => m.CodeRaisonBIUpdateComponent),
    resolve: {
      codeRaisonBI: CodeRaisonBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default codeRaisonBIRoute;
