import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationLocation, NewOrganizationLocation } from '../organization-location.model';

export type PartialUpdateOrganizationLocation = Partial<IOrganizationLocation> & Pick<IOrganizationLocation, 'id'>;

export type EntityResponseType = HttpResponse<IOrganizationLocation>;
export type EntityArrayResponseType = HttpResponse<IOrganizationLocation[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationLocationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-locations');

  create(organizationLocation: NewOrganizationLocation): Observable<EntityResponseType> {
    return this.http.post<IOrganizationLocation>(this.resourceUrl, organizationLocation, { observe: 'response' });
  }

  update(organizationLocation: IOrganizationLocation): Observable<EntityResponseType> {
    return this.http.put<IOrganizationLocation>(
      `${this.resourceUrl}/${this.getOrganizationLocationIdentifier(organizationLocation)}`,
      organizationLocation,
      { observe: 'response' },
    );
  }

  partialUpdate(organizationLocation: PartialUpdateOrganizationLocation): Observable<EntityResponseType> {
    return this.http.patch<IOrganizationLocation>(
      `${this.resourceUrl}/${this.getOrganizationLocationIdentifier(organizationLocation)}`,
      organizationLocation,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganizationLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganizationLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganizationLocationIdentifier(organizationLocation: Pick<IOrganizationLocation, 'id'>): number {
    return organizationLocation.id;
  }

  compareOrganizationLocation(o1: Pick<IOrganizationLocation, 'id'> | null, o2: Pick<IOrganizationLocation, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrganizationLocationIdentifier(o1) === this.getOrganizationLocationIdentifier(o2) : o1 === o2;
  }

  addOrganizationLocationToCollectionIfMissing<Type extends Pick<IOrganizationLocation, 'id'>>(
    organizationLocationCollection: Type[],
    ...organizationLocationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organizationLocations: Type[] = organizationLocationsToCheck.filter(isPresent);
    if (organizationLocations.length > 0) {
      const organizationLocationCollectionIdentifiers = organizationLocationCollection.map(organizationLocationItem =>
        this.getOrganizationLocationIdentifier(organizationLocationItem),
      );
      const organizationLocationsToAdd = organizationLocations.filter(organizationLocationItem => {
        const organizationLocationIdentifier = this.getOrganizationLocationIdentifier(organizationLocationItem);
        if (organizationLocationCollectionIdentifiers.includes(organizationLocationIdentifier)) {
          return false;
        }
        organizationLocationCollectionIdentifiers.push(organizationLocationIdentifier);
        return true;
      });
      return [...organizationLocationsToAdd, ...organizationLocationCollection];
    }
    return organizationLocationCollection;
  }
}
