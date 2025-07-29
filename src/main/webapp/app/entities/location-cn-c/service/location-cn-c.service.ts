import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILocationCnC, NewLocationCnC } from '../location-cn-c.model';

export type PartialUpdateLocationCnC = Partial<ILocationCnC> & Pick<ILocationCnC, 'id'>;

export type EntityResponseType = HttpResponse<ILocationCnC>;
export type EntityArrayResponseType = HttpResponse<ILocationCnC[]>;

@Injectable({ providedIn: 'root' })
export class LocationCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/location-cn-cs');

  create(locationCnC: NewLocationCnC): Observable<EntityResponseType> {
    return this.http.post<ILocationCnC>(this.resourceUrl, locationCnC, { observe: 'response' });
  }

  update(locationCnC: ILocationCnC): Observable<EntityResponseType> {
    return this.http.put<ILocationCnC>(`${this.resourceUrl}/${this.getLocationCnCIdentifier(locationCnC)}`, locationCnC, {
      observe: 'response',
    });
  }

  partialUpdate(locationCnC: PartialUpdateLocationCnC): Observable<EntityResponseType> {
    return this.http.patch<ILocationCnC>(`${this.resourceUrl}/${this.getLocationCnCIdentifier(locationCnC)}`, locationCnC, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocationCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocationCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLocationCnCIdentifier(locationCnC: Pick<ILocationCnC, 'id'>): number {
    return locationCnC.id;
  }

  compareLocationCnC(o1: Pick<ILocationCnC, 'id'> | null, o2: Pick<ILocationCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getLocationCnCIdentifier(o1) === this.getLocationCnCIdentifier(o2) : o1 === o2;
  }

  addLocationCnCToCollectionIfMissing<Type extends Pick<ILocationCnC, 'id'>>(
    locationCnCCollection: Type[],
    ...locationCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const locationCnCS: Type[] = locationCnCSToCheck.filter(isPresent);
    if (locationCnCS.length > 0) {
      const locationCnCCollectionIdentifiers = locationCnCCollection.map(locationCnCItem => this.getLocationCnCIdentifier(locationCnCItem));
      const locationCnCSToAdd = locationCnCS.filter(locationCnCItem => {
        const locationCnCIdentifier = this.getLocationCnCIdentifier(locationCnCItem);
        if (locationCnCCollectionIdentifiers.includes(locationCnCIdentifier)) {
          return false;
        }
        locationCnCCollectionIdentifiers.push(locationCnCIdentifier);
        return true;
      });
      return [...locationCnCSToAdd, ...locationCnCCollection];
    }
    return locationCnCCollection;
  }
}
