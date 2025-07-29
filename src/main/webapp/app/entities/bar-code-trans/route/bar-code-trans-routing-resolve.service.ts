import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBarCodeTrans } from '../bar-code-trans.model';
import { BarCodeTransService } from '../service/bar-code-trans.service';

const barCodeTransResolve = (route: ActivatedRouteSnapshot): Observable<null | IBarCodeTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(BarCodeTransService)
      .find(id)
      .pipe(
        mergeMap((barCodeTrans: HttpResponse<IBarCodeTrans>) => {
          if (barCodeTrans.body) {
            return of(barCodeTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default barCodeTransResolve;
