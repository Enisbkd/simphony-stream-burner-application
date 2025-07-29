import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationLocation } from '../organization-location.model';
import { OrganizationLocationService } from '../service/organization-location.service';

const organizationLocationResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrganizationLocation> => {
  const id = route.params.id;
  if (id) {
    return inject(OrganizationLocationService)
      .find(id)
      .pipe(
        mergeMap((organizationLocation: HttpResponse<IOrganizationLocation>) => {
          if (organizationLocation.body) {
            return of(organizationLocation.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default organizationLocationResolve;
