import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILocationCnC } from '../location-cn-c.model';
import { LocationCnCService } from '../service/location-cn-c.service';

const locationCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | ILocationCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(LocationCnCService)
      .find(id)
      .pipe(
        mergeMap((locationCnC: HttpResponse<ILocationCnC>) => {
          if (locationCnC.body) {
            return of(locationCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default locationCnCResolve;
