import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { PointDeVenteCnCService } from '../service/point-de-vente-cn-c.service';

const pointDeVenteCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IPointDeVenteCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(PointDeVenteCnCService)
      .find(id)
      .pipe(
        mergeMap((pointDeVenteCnC: HttpResponse<IPointDeVenteCnC>) => {
          if (pointDeVenteCnC.body) {
            return of(pointDeVenteCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pointDeVenteCnCResolve;
