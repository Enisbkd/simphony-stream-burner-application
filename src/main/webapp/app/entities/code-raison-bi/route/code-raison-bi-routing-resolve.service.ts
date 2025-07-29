import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICodeRaisonBI } from '../code-raison-bi.model';
import { CodeRaisonBIService } from '../service/code-raison-bi.service';

const codeRaisonBIResolve = (route: ActivatedRouteSnapshot): Observable<null | ICodeRaisonBI> => {
  const id = route.params.id;
  if (id) {
    return inject(CodeRaisonBIService)
      .find(id)
      .pipe(
        mergeMap((codeRaisonBI: HttpResponse<ICodeRaisonBI>) => {
          if (codeRaisonBI.body) {
            return of(codeRaisonBI.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default codeRaisonBIResolve;
