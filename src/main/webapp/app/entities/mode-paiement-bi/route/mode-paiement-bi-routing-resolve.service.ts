import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModePaiementBI } from '../mode-paiement-bi.model';
import { ModePaiementBIService } from '../service/mode-paiement-bi.service';

const modePaiementBIResolve = (route: ActivatedRouteSnapshot): Observable<null | IModePaiementBI> => {
  const id = route.params.id;
  if (id) {
    return inject(ModePaiementBIService)
      .find(id)
      .pipe(
        mergeMap((modePaiementBI: HttpResponse<IModePaiementBI>) => {
          if (modePaiementBI.body) {
            return of(modePaiementBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default modePaiementBIResolve;
