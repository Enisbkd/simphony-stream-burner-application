import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHierarchieCnC } from '../hierarchie-cn-c.model';
import { HierarchieCnCService } from '../service/hierarchie-cn-c.service';

const hierarchieCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IHierarchieCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(HierarchieCnCService)
      .find(id)
      .pipe(
        mergeMap((hierarchieCnC: HttpResponse<IHierarchieCnC>) => {
          if (hierarchieCnC.body) {
            return of(hierarchieCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default hierarchieCnCResolve;
