import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRemiseBI } from '../remise-bi.model';
import { RemiseBIService } from '../service/remise-bi.service';

const remiseBIResolve = (route: ActivatedRouteSnapshot): Observable<null | IRemiseBI> => {
  const id = route.params.id;
  if (id) {
    return inject(RemiseBIService)
      .find(id)
      .pipe(
        mergeMap((remiseBI: HttpResponse<IRemiseBI>) => {
          if (remiseBI.body) {
            return of(remiseBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default remiseBIResolve;
