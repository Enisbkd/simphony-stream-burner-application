import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHierarchie } from '../hierarchie.model';
import { HierarchieService } from '../service/hierarchie.service';

const hierarchieResolve = (route: ActivatedRouteSnapshot): Observable<null | IHierarchie> => {
  const id = route.params.id;
  if (id) {
    return inject(HierarchieService)
      .find(id)
      .pipe(
        mergeMap((hierarchie: HttpResponse<IHierarchie>) => {
          if (hierarchie.body) {
            return of(hierarchie.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default hierarchieResolve;
