import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommissionServiceBI } from '../commission-service-bi.model';
import { CommissionServiceBIService } from '../service/commission-service-bi.service';

const commissionServiceBIResolve = (route: ActivatedRouteSnapshot): Observable<null | ICommissionServiceBI> => {
  const id = route.params.id;
  if (id) {
    return inject(CommissionServiceBIService)
      .find(id)
      .pipe(
        mergeMap((commissionServiceBI: HttpResponse<ICommissionServiceBI>) => {
          if (commissionServiceBI.body) {
            return of(commissionServiceBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default commissionServiceBIResolve;
