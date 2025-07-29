import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMenuItemMastersCnC, NewMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';

export type PartialUpdateMenuItemMastersCnC = Partial<IMenuItemMastersCnC> & Pick<IMenuItemMastersCnC, 'id'>;

export type EntityResponseType = HttpResponse<IMenuItemMastersCnC>;
export type EntityArrayResponseType = HttpResponse<IMenuItemMastersCnC[]>;

@Injectable({ providedIn: 'root' })
export class MenuItemMastersCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/menu-item-masters-cn-cs');

  create(menuItemMastersCnC: NewMenuItemMastersCnC): Observable<EntityResponseType> {
    return this.http.post<IMenuItemMastersCnC>(this.resourceUrl, menuItemMastersCnC, { observe: 'response' });
  }

  update(menuItemMastersCnC: IMenuItemMastersCnC): Observable<EntityResponseType> {
    return this.http.put<IMenuItemMastersCnC>(
      `${this.resourceUrl}/${this.getMenuItemMastersCnCIdentifier(menuItemMastersCnC)}`,
      menuItemMastersCnC,
      { observe: 'response' },
    );
  }

  partialUpdate(menuItemMastersCnC: PartialUpdateMenuItemMastersCnC): Observable<EntityResponseType> {
    return this.http.patch<IMenuItemMastersCnC>(
      `${this.resourceUrl}/${this.getMenuItemMastersCnCIdentifier(menuItemMastersCnC)}`,
      menuItemMastersCnC,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenuItemMastersCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenuItemMastersCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMenuItemMastersCnCIdentifier(menuItemMastersCnC: Pick<IMenuItemMastersCnC, 'id'>): number {
    return menuItemMastersCnC.id;
  }

  compareMenuItemMastersCnC(o1: Pick<IMenuItemMastersCnC, 'id'> | null, o2: Pick<IMenuItemMastersCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getMenuItemMastersCnCIdentifier(o1) === this.getMenuItemMastersCnCIdentifier(o2) : o1 === o2;
  }

  addMenuItemMastersCnCToCollectionIfMissing<Type extends Pick<IMenuItemMastersCnC, 'id'>>(
    menuItemMastersCnCCollection: Type[],
    ...menuItemMastersCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const menuItemMastersCnCS: Type[] = menuItemMastersCnCSToCheck.filter(isPresent);
    if (menuItemMastersCnCS.length > 0) {
      const menuItemMastersCnCCollectionIdentifiers = menuItemMastersCnCCollection.map(menuItemMastersCnCItem =>
        this.getMenuItemMastersCnCIdentifier(menuItemMastersCnCItem),
      );
      const menuItemMastersCnCSToAdd = menuItemMastersCnCS.filter(menuItemMastersCnCItem => {
        const menuItemMastersCnCIdentifier = this.getMenuItemMastersCnCIdentifier(menuItemMastersCnCItem);
        if (menuItemMastersCnCCollectionIdentifiers.includes(menuItemMastersCnCIdentifier)) {
          return false;
        }
        menuItemMastersCnCCollectionIdentifiers.push(menuItemMastersCnCIdentifier);
        return true;
      });
      return [...menuItemMastersCnCSToAdd, ...menuItemMastersCnCCollection];
    }
    return menuItemMastersCnCCollection;
  }
}
