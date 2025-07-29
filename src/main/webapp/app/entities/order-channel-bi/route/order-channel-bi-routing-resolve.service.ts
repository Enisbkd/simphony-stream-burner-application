import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderChannelBI } from '../order-channel-bi.model';
import { OrderChannelBIService } from '../service/order-channel-bi.service';

const orderChannelBIResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrderChannelBI> => {
  const id = route.params.id;
  if (id) {
    return inject(OrderChannelBIService)
      .find(id)
      .pipe(
        mergeMap((orderChannelBI: HttpResponse<IOrderChannelBI>) => {
          if (orderChannelBI.body) {
            return of(orderChannelBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default orderChannelBIResolve;
