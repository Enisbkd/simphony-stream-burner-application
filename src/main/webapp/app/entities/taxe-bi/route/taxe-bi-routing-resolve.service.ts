import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaxeBI } from '../taxe-bi.model';
import { TaxeBIService } from '../service/taxe-bi.service';

const taxeBIResolve = (route: ActivatedRouteSnapshot): Observable<null | ITaxeBI> => {
  const id = route.params.id;
  if (id) {
    return inject(TaxeBIService)
      .find(id)
      .pipe(
        mergeMap((taxeBI: HttpResponse<ITaxeBI>) => {
          if (taxeBI.body) {
            return of(taxeBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default taxeBIResolve;
