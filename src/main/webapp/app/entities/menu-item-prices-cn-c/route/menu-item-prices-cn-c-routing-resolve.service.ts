import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';
import { MenuItemPricesCnCService } from '../service/menu-item-prices-cn-c.service';

const menuItemPricesCnCResolve = (route: ActivatedRouteSnapshot): Observable<null | IMenuItemPricesCnC> => {
  const id = route.params.id;
  if (id) {
    return inject(MenuItemPricesCnCService)
      .find(id)
      .pipe(
        mergeMap((menuItemPricesCnC: HttpResponse<IMenuItemPricesCnC>) => {
          if (menuItemPricesCnC.body) {
            return of(menuItemPricesCnC.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default menuItemPricesCnCResolve;
