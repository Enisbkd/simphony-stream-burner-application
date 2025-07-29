import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';
import { MenuItemMastersCnCService } from '../service/menu-item-masters-cn-c.service';

const menuItemMastersCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IMenuItemMastersCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(MenuItemMastersCnCService)
      .find(id)
      .pipe(
        mergeMap((menuItemMastersCnC: HttpResponse<IMenuItemMastersCnC>) => {
          if (menuItemMastersCnC.body) {
            return of(menuItemMastersCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default menuItemMastersCnCResolve;
