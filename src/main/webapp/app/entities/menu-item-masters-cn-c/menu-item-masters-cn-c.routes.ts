import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MenuItemMastersCnCResolve from './route/menu-item-masters-cn-c-routing-resolve.service';

const menuItemMastersCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/menu-item-masters-cn-c.component').then(m => m.MenuItemMastersCnCComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/menu-item-masters-cn-c-detail.component').then(m => m.MenuItemMastersCnCDetailComponent),
    resolve: {
      menuItemMastersCnC: MenuItemMastersCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/menu-item-masters-cn-c-update.component').then(m => m.MenuItemMastersCnCUpdateComponent),
    resolve: {
      menuItemMastersCnC: MenuItemMastersCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/menu-item-masters-cn-c-update.component').then(m => m.MenuItemMastersCnCUpdateComponent),
    resolve: {
      menuItemMastersCnC: MenuItemMastersCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default menuItemMastersCnCRoute;
