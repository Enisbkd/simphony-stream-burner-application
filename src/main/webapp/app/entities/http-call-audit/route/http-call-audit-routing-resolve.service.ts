import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHttpCallAudit } from '../http-call-audit.model';
import { HttpCallAuditService } from '../service/http-call-audit.service';

const httpCallAuditResolve = (route: ActivatedRouteSnapshot): Observable<null | IHttpCallAudit> => {
  const id = route.params.id;
  if (id) {
    return inject(HttpCallAuditService)
      .find(id)
      .pipe(
        mergeMap((httpCallAudit: HttpResponse<IHttpCallAudit>) => {
          if (httpCallAudit.body) {
            return of(httpCallAudit.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default httpCallAuditResolve;
