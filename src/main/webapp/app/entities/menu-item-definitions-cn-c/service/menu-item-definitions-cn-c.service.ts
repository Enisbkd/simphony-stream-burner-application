import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMenuItemDefinitionsCnC, NewMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';

export type PartialUpdateMenuItemDefinitionsCnC = Partial<IMenuItemDefinitionsCnC> & Pick<IMenuItemDefinitionsCnC, 'id'>;

export type EntityResponseType = HttpResponse<IMenuItemDefinitionsCnC>;
export type EntityArrayResponseType = HttpResponse<IMenuItemDefinitionsCnC[]>;

@Injectable({ providedIn: 'root' })
export class MenuItemDefinitionsCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/menu-item-definitions-cn-cs');

  create(menuItemDefinitionsCnC: NewMenuItemDefinitionsCnC): Observable<EntityResponseType> {
    return this.http.post<IMenuItemDefinitionsCnC>(this.resourceUrl, menuItemDefinitionsCnC, { observe: 'response' });
  }

  update(menuItemDefinitionsCnC: IMenuItemDefinitionsCnC): Observable<EntityResponseType> {
    return this.http.put<IMenuItemDefinitionsCnC>(
      `${this.resourceUrl}/${this.getMenuItemDefinitionsCnCIdentifier(menuItemDefinitionsCnC)}`,
      menuItemDefinitionsCnC,
      { observe: 'response' },
    );
  }

  partialUpdate(menuItemDefinitionsCnC: PartialUpdateMenuItemDefinitionsCnC): Observable<EntityResponseType> {
    return this.http.patch<IMenuItemDefinitionsCnC>(
      `${this.resourceUrl}/${this.getMenuItemDefinitionsCnCIdentifier(menuItemDefinitionsCnC)}`,
      menuItemDefinitionsCnC,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMenuItemDefinitionsCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMenuItemDefinitionsCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMenuItemDefinitionsCnCIdentifier(menuItemDefinitionsCnC: Pick<IMenuItemDefinitionsCnC, 'id'>): number {
    return menuItemDefinitionsCnC.id;
  }

  compareMenuItemDefinitionsCnC(o1: Pick<IMenuItemDefinitionsCnC, 'id'> | null, o2: Pick<IMenuItemDefinitionsCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getMenuItemDefinitionsCnCIdentifier(o1) === this.getMenuItemDefinitionsCnCIdentifier(o2) : o1 === o2;
  }

  addMenuItemDefinitionsCnCToCollectionIfMissing<Type extends Pick<IMenuItemDefinitionsCnC, 'id'>>(
    menuItemDefinitionsCnCCollection: Type[],
    ...menuItemDefinitionsCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const menuItemDefinitionsCnCS: Type[] = menuItemDefinitionsCnCSToCheck.filter(isPresent);
    if (menuItemDefinitionsCnCS.length > 0) {
      const menuItemDefinitionsCnCCollectionIdentifiers = menuItemDefinitionsCnCCollection.map(menuItemDefinitionsCnCItem =>
        this.getMenuItemDefinitionsCnCIdentifier(menuItemDefinitionsCnCItem),
      );
      const menuItemDefinitionsCnCSToAdd = menuItemDefinitionsCnCS.filter(menuItemDefinitionsCnCItem => {
        const menuItemDefinitionsCnCIdentifier = this.getMenuItemDefinitionsCnCIdentifier(menuItemDefinitionsCnCItem);
        if (menuItemDefinitionsCnCCollectionIdentifiers.includes(menuItemDefinitionsCnCIdentifier)) {
          return false;
        }
        menuItemDefinitionsCnCCollectionIdentifiers.push(menuItemDefinitionsCnCIdentifier);
        return true;
      });
      return [...menuItemDefinitionsCnCSToAdd, ...menuItemDefinitionsCnCCollection];
    }
    return menuItemDefinitionsCnCCollection;
  }
}
