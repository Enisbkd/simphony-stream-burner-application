import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFamilyGroupCnC } from '../family-group-cn-c.model';
import { FamilyGroupCnCService } from '../service/family-group-cn-c.service';

const familyGroupCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IFamilyGroupCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(FamilyGroupCnCService)
      .find(id)
      .pipe(
        mergeMap((familyGroupCnC: HttpResponse<IFamilyGroupCnC>) => {
          if (familyGroupCnC.body) {
            return of(familyGroupCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default familyGroupCnCResolve;
