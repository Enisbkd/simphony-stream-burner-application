import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocieteTrans } from '../societe-trans.model';
import { SocieteTransService } from '../service/societe-trans.service';

const societeTransResolve = (route: ActivatedRouteSnapshot): Observable<null | ISocieteTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(SocieteTransService)
      .find(id)
      .pipe(
        mergeMap((societeTrans: HttpResponse<ISocieteTrans>) => {
          if (societeTrans.body) {
            return of(societeTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default societeTransResolve;
