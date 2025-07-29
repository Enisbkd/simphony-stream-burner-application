import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderType } from '../order-type.model';
import { OrderTypeService } from '../service/order-type.service';

const orderTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrderType> => {
  const id = route.params.id;
  if (id) {
    return inject(OrderTypeService)
      .find(id)
      .pipe(
        mergeMap((orderType: HttpResponse<IOrderType>) => {
          if (orderType.body) {
            return of(orderType.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default orderTypeResolve;
