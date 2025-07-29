import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MenuItemPricesCnCResolve from './route/menu-item-prices-cn-c-routing-resolve.service';

const menuItemPricesCnCRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/menu-item-prices-cn-c.component').then(m => m.MenuItemPricesCnCComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/menu-item-prices-cn-c-detail.component').then(m => m.MenuItemPricesCnCDetailComponent),
    resolve: {
      menuItemPricesCnC: MenuItemPricesCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/menu-item-prices-cn-c-update.component').then(m => m.MenuItemPricesCnCUpdateComponent),
    resolve: {
      menuItemPricesCnC: MenuItemPricesCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/menu-item-prices-cn-c-update.component').then(m => m.MenuItemPricesCnCUpdateComponent),
    resolve: {
      menuItemPricesCnC: MenuItemPricesCnCResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default menuItemPricesCnCRoute;
