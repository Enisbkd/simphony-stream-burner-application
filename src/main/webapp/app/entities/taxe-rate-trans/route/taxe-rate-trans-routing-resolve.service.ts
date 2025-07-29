import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaxeRateTrans } from '../taxe-rate-trans.model';
import { TaxeRateTransService } from '../service/taxe-rate-trans.service';

const taxeRateTransResolve = (route: ActivatedRouteSnapshot): Observable<null | ITaxeRateTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(TaxeRateTransService)
      .find(id)
      .pipe(
        mergeMap((taxeRateTrans: HttpResponse<ITaxeRateTrans>) => {
          if (taxeRateTrans.body) {
            return of(taxeRateTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default taxeRateTransResolve;
