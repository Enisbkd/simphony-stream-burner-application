import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointDeVenteTrans } from '../point-de-vente-trans.model';
import { PointDeVenteTransService } from '../service/point-de-vente-trans.service';

const pointDeVenteTransResolve = (route: ActivatedRouteSnapshot): Observable<null | IPointDeVenteTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(PointDeVenteTransService)
      .find(id)
      .pipe(
        mergeMap((pointDeVenteTrans: HttpResponse<IPointDeVenteTrans>) => {
          if (pointDeVenteTrans.body) {
            return of(pointDeVenteTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pointDeVenteTransResolve;
