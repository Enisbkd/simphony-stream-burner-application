import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuestCheckBI } from '../guest-check-bi.model';
import { GuestCheckBIService } from '../service/guest-check-bi.service';

const guestCheckBIResolve = (route: ActivatedRouteSnapshot): Observable<null | IGuestCheckBI> => {
  const id = route.params.id;
  if (id) {
    return inject(GuestCheckBIService)
      .find(id)
      .pipe(
        mergeMap((guestCheckBI: HttpResponse<IGuestCheckBI>) => {
          if (guestCheckBI.body) {
            return of(guestCheckBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default guestCheckBIResolve;
