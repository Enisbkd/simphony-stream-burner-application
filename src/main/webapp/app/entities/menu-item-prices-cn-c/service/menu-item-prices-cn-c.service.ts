import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMenuItemPricesCnC, NewMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';

export type PartialUpdateMenuItemPricesCnC = Partial<IMenuItemPricesCnC> & Pick<IMenuItemPricesCnC, 'id'>;

export type EntityResponseType = HttpResponse<IMenuItemPricesCnC>;
export type EntityArrayResponseType = HttpResponse<IMenuItemPricesCnC[]>;

@Injectable({ providedIn: 'root' })
export class MenuItemPricesCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/menu-item-prices-cn-cs');

  create(menuItemPricesCnC: NewMenuItemPricesCnC): Observable<EntityResponseType> {
    return this.http.post<IMenuItemPricesCnC>(this.resourceUrl, menuItemPricesCnC, { observe: 'response' });
  }

  update(menuItemPricesCnC: IMenuItemPricesCnC): Observable<EntityResponseType> {
    return this.http.put<IMenuItemPricesCnC>(
      `${this.resourceUrl}/${this.getMenuItemPricesCnCIdentifier(menuItemPricesCnC)}`,
      menuItemPricesCnC,
      { observe: 'response' },
    );
  }

  partialUpdate(menuItemPricesCnC: PartialUpdateMenuItemPricesCnC): Observable<EntityResponseType> {
    return this.http.patch<IMenuItemPricesCnC>(
      `${this.resourceUrl}/${this.getMenuItemPricesCnCIdentifier(menuItemPricesCnC)}`,
      menuItemPricesCnC,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenuItemPricesCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenuItemPricesCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMenuItemPricesCnCIdentifier(menuItemPricesCnC: Pick<IMenuItemPricesCnC, 'id'>): number {
    return menuItemPricesCnC.id;
  }

  compareMenuItemPricesCnC(o1: Pick<IMenuItemPricesCnC, 'id'> | null, o2: Pick<IMenuItemPricesCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getMenuItemPricesCnCIdentifier(o1) === this.getMenuItemPricesCnCIdentifier(o2) : o1 === o2;
  }

  addMenuItemPricesCnCToCollectionIfMissing<Type extends Pick<IMenuItemPricesCnC, 'id'>>(
    menuItemPricesCnCCollection: Type[],
    ...menuItemPricesCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const menuItemPricesCnCS: Type[] = menuItemPricesCnCSToCheck.filter(isPresent);
    if (menuItemPricesCnCS.length > 0) {
      const menuItemPricesCnCCollectionIdentifiers = menuItemPricesCnCCollection.map(menuItemPricesCnCItem =>
        this.getMenuItemPricesCnCIdentifier(menuItemPricesCnCItem),
      );
      const menuItemPricesCnCSToAdd = menuItemPricesCnCS.filter(menuItemPricesCnCItem => {
        const menuItemPricesCnCIdentifier = this.getMenuItemPricesCnCIdentifier(menuItemPricesCnCItem);
        if (menuItemPricesCnCCollectionIdentifiers.includes(menuItemPricesCnCIdentifier)) {
          return false;
        }
        menuItemPricesCnCCollectionIdentifiers.push(menuItemPricesCnCIdentifier);
        return true;
      });
      return [...menuItemPricesCnCSToAdd, ...menuItemPricesCnCCollection];
    }
    return menuItemPricesCnCCollection;
  }
}
