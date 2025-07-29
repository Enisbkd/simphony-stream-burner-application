import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModePaiementTrans } from '../mode-paiement-trans.model';
import { ModePaiementTransService } from '../service/mode-paiement-trans.service';

const modePaiementTransResolve = (route: ActivatedRouteSnapshot): Observable<null | IModePaiementTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(ModePaiementTransService)
      .find(id)
      .pipe(
        mergeMap((modePaiementTrans: HttpResponse<IModePaiementTrans>) => {
          if (modePaiementTrans.body) {
            return of(modePaiementTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default modePaiementTransResolve;
