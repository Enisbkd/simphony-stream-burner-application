import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MenuItemDefinitionsCnCResolve from './route/menu-item-definitions-cn-c-routing-resolve.service';

const menuItemDefinitionsCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/menu-item-definitions-cn-c.component').then(m => m.MenuItemDefinitionsCnCComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/menu-item-definitions-cn-c-detail.component').then(m => m.MenuItemDefinitionsCnCDetailComponent),
    resolve: {
      menuItemDefinitionsCnC: MenuItemDefinitionsCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/menu-item-definitions-cn-c-update.component').then(m => m.MenuItemDefinitionsCnCUpdateComponent),
    resolve: {
      menuItemDefinitionsCnC: MenuItemDefinitionsCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/menu-item-definitions-cn-c-update.component').then(m => m.MenuItemDefinitionsCnCUpdateComponent),
    resolve: {
      menuItemDefinitionsCnC: MenuItemDefinitionsCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default menuItemDefinitionsCnCRoute;
