import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ModePaiementTransResolve from './route/mode-paiement-trans-routing-resolve.service';

const modePaiementTransRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/mode-paiement-trans.component').then(m => m.ModePaiementTransComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/mode-paiement-trans-detail.component').then(m => m.ModePaiementTransDetailComponent),
    resolve: {
      modePaiementTrans: ModePaiementTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/mode-paiement-trans-update.component').then(m => m.ModePaiementTransUpdateComponent),
    resolve: {
      modePaiementTrans: ModePaiementTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/mode-paiement-trans-update.component').then(m => m.ModePaiementTransUpdateComponent),
    resolve: {
      modePaiementTrans: ModePaiementTransResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default modePaiementTransRoute;
