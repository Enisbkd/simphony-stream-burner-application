import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderTypeBI } from '../order-type-bi.model';
import { OrderTypeBIService } from '../service/order-type-bi.service';

const orderTypeBIResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrderTypeBI> => {
  const id = route.params.id;
  if (id) {
    return inject(OrderTypeBIService)
      .find(id)
      .pipe(
        mergeMap((orderTypeBI: HttpResponse<IOrderTypeBI>) => {
          if (orderTypeBI.body) {
            return of(orderTypeBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default orderTypeBIResolve;
