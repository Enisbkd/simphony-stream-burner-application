import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployeeCnC } from '../employee-cn-c.model';
import { EmployeeCnCService } from '../service/employee-cn-c.service';

const employeeCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmployeeCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(EmployeeCnCService)
      .find(id)
      .pipe(
        mergeMap((employeeCnC: HttpResponse<IEmployeeCnC>) => {
          if (employeeCnC.body) {
            return of(employeeCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default employeeCnCResolve;
