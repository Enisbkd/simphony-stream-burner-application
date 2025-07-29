import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetailLineBI } from '../detail-line-bi.model';
import { DetailLineBIService } from '../service/detail-line-bi.service';

const detailLineBIResolve = (route: ActivatedRouteSnapshot): Observable<null | IDetailLineBI> => {
  const id = route.params.id;
  if (id) {
    return inject(DetailLineBIService)
      .find(id)
      .pipe(
        mergeMap((detailLineBI: HttpResponse<IDetailLineBI>) => {
          if (detailLineBI.body) {
            return of(detailLineBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default detailLineBIResolve;
