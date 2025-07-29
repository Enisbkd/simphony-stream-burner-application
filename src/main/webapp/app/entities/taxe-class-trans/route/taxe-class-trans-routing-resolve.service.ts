import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaxeClassTrans } from '../taxe-class-trans.model';
import { TaxeClassTransService } from '../service/taxe-class-trans.service';

const taxeClassTransResolve = (route: ActivatedRouteSnapshot): Observable<null | ITaxeClassTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(TaxeClassTransService)
      .find(id)
      .pipe(
        mergeMap((taxeClassTrans: HttpResponse<ITaxeClassTrans>) => {
          if (taxeClassTrans.body) {
            return of(taxeClassTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default taxeClassTransResolve;
