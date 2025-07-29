import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderChannel } from '../order-channel.model';
import { OrderChannelService } from '../service/order-channel.service';

const orderChannelResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrderChannel> => {
  const id = route.params.id;
  if (id) {
    return inject(OrderChannelService)
      .find(id)
      .pipe(
        mergeMap((orderChannel: HttpResponse<IOrderChannel>) => {
          if (orderChannel.body) {
            return of(orderChannel.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default orderChannelResolve;
