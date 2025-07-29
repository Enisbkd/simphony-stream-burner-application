import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckTrans } from '../check-trans.model';
import { CheckTransService } from '../service/check-trans.service';

const checkTransResolve = (route: ActivatedRouteSnapshot): Observable<null | ICheckTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(CheckTransService)
      .find(id)
      .pipe(
        mergeMap((checkTrans: HttpResponse<ICheckTrans>) => {
          if (checkTrans.body) {
            return of(checkTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default checkTransResolve;
