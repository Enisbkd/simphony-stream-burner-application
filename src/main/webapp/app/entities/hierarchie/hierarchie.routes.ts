import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import HierarchieResolve from './route/hierarchie-routing-resolve.service';

const hierarchieRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/hierarchie.component').then(m => m.HierarchieComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/hierarchie-detail.component').then(m => m.HierarchieDetailComponent),
    resolve: {
      hierarchie: HierarchieResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/hierarchie-update.component').then(m => m.HierarchieUpdateComponent),
    resolve: {
      hierarchie: HierarchieResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/hierarchie-update.component').then(m => m.HierarchieUpdateComponent),
    resolve: {
      hierarchie: HierarchieResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default hierarchieRoute;
