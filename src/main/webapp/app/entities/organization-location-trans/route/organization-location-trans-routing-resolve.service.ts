import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationLocationTrans } from '../organization-location-trans.model';
import { OrganizationLocationTransService } from '../service/organization-location-trans.service';

const organizationLocationTransResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrganizationLocationTrans> => {
  const id = route.params.id;
  if (id) {
    return inject(OrganizationLocationTransService)
      .find(id)
      .pipe(
        mergeMap((organizationLocationTrans: HttpResponse<IOrganizationLocationTrans>) => {
          if (organizationLocationTrans.body) {
            return of(organizationLocationTrans.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default organizationLocationTransResolve;
