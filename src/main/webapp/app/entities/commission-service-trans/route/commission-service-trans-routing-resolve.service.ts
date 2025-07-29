import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommissionServiceTrans } from '../commission-service-trans.model';
import { CommissionServiceTransService } from '../service/commission-service-trans.service';

const commissionServiceTransResolve = (route: ActivatedRouteSnapshot): Observable<null | ICommissionServiceTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(CommissionServiceTransService)
      .find(id)
      .pipe(
        mergeMap((commissionServiceTrans: HttpResponse<ICommissionServiceTrans>) => {
          if (commissionServiceTrans.body) {
            return of(commissionServiceTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default commissionServiceTransResolve;
