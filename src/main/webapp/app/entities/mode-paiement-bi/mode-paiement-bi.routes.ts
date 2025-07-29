import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ModePaiementBIResolve from './route/mode-paiement-bi-routing-resolve.service';

const modePaiementBIRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/mode-paiement-bi.component').then(m => m.ModePaiementBIComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/mode-paiement-bi-detail.component').then(m => m.ModePaiementBIDetailComponent),
    resolve: {
      modePaiementBI: ModePaiementBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/mode-paiement-bi-update.component').then(m => m.ModePaiementBIUpdateComponent),
    resolve: {
      modePaiementBI: ModePaiementBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/mode-paiement-bi-update.component').then(m => m.ModePaiementBIUpdateComponent),
    resolve: {
      modePaiementBI: ModePaiementBIResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default modePaiementBIRoute;
