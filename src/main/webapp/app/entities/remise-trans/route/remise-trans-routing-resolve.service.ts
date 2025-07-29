import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRemiseTrans } from '../remise-trans.model';
import { RemiseTransService } from '../service/remise-trans.service';

const remiseTransResolve = (route: ActivatedRouteSnapshot): Observable<null | IRemiseTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(RemiseTransService)
      .find(id)
      .pipe(
        mergeMap((remiseTrans: HttpResponse<IRemiseTrans>) => {
          if (remiseTrans.body) {
            return of(remiseTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default remiseTransResolve;
