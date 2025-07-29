import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';
import { MenuItemDefinitionsCnCService } from '../service/menu-item-definitions-cn-c.service';

const menuItemDefinitionsCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IMenuItemDefinitionsCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(MenuItemDefinitionsCnCService)
      .find(id)
      .pipe(
        mergeMap((menuItemDefinitionsCnC: HttpResponse<IMenuItemDefinitionsCnC>) => {
          if (menuItemDefinitionsCnC.body) {
            return of(menuItemDefinitionsCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default menuItemDefinitionsCnCResolve;
