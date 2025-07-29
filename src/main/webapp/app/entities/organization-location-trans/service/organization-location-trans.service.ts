import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationLocationTrans, NewOrganizationLocationTrans } from '../organization-location-trans.model';

export type PartialUpdateOrganizationLocationTrans = Partial<IOrganizationLocationTrans> & Pick<IOrganizationLocationTrans, 'id'>;

export type EntityResponseType = HttpResponse<IOrganizationLocationTrans>;
export type EntityArrayResponseType = HttpResponse<IOrganizationLocationTrans[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationLocationTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-location-trans');

  create(organizationLocationTrans: NewOrganizationLocationTrans): Observable<EntityResponseType> {
    return this.http.post<IOrganizationLocationTrans>(this.resourceUrl, organizationLocationTrans, { observe: 'response' });
  }

  update(organizationLocationTrans: IOrganizationLocationTrans): Observable<EntityResponseType> {
    return this.http.put<IOrganizationLocationTrans>(
      `${this.resourceUrl}/${this.getOrganizationLocationTransIdentifier(organizationLocationTrans)}`,
      organizationLocationTrans,
      { observe: 'response' },
    );
  }

  partialUpdate(organizationLocationTrans: PartialUpdateOrganizationLocationTrans): Observable<EntityResponseType> {
    return this.http.patch<IOrganizationLocationTrans>(
      `${this.resourceUrl}/${this.getOrganizationLocationTransIdentifier(organizationLocationTrans)}`,
      organizationLocationTrans,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganizationLocationTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganizationLocationTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganizationLocationTransIdentifier(organizationLocationTrans: Pick<IOrganizationLocationTrans, 'id'>): number {
    return organizationLocationTrans.id;
  }

  compareOrganizationLocationTrans(
    o1: Pick<IOrganizationLocationTrans, 'id'> | null,
    o2: Pick<IOrganizationLocationTrans, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getOrganizationLocationTransIdentifier(o1) === this.getOrganizationLocationTransIdentifier(o2) : o1 === o2;
  }

  addOrganizationLocationTransToCollectionIfMissing<Type extends Pick<IOrganizationLocationTrans, 'id'>>(
    organizationLocationTransCollection: Type[],
    ...organizationLocationTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organizationLocationTrans: Type[] = organizationLocationTransToCheck.filter(isPresent);
    if (organizationLocationTrans.length > 0) {
      const organizationLocationTransCollectionIdentifiers = organizationLocationTransCollection.map(organizationLocationTransItem =>
        this.getOrganizationLocationTransIdentifier(organizationLocationTransItem),
      );
      const organizationLocationTransToAdd = organizationLocationTrans.filter(organizationLocationTransItem => {
        const organizationLocationTransIdentifier = this.getOrganizationLocationTransIdentifier(organizationLocationTransItem);
        if (organizationLocationTransCollectionIdentifiers.includes(organizationLocationTransIdentifier)) {
          return false;
        }
        organizationLocationTransCollectionIdentifiers.push(organizationLocationTransIdentifier);
        return true;
      });
      return [...organizationLocationTransToAdd, ...organizationLocationTransCollection];
    }
    return organizationLocationTransCollection;
  }
}
