import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMajorGroupCnC } from '../major-group-cn-c.model';
import { MajorGroupCnCService } from '../service/major-group-cn-c.service';

const majorGroupCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IMajorGroupCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(MajorGroupCnCService)
      .find(id)
      .pipe(
        mergeMap((majorGroupCnC: HttpResponse<IMajorGroupCnC>) => {
          if (majorGroupCnC.body) {
            return of(majorGroupCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default majorGroupCnCResolve;
