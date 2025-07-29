import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMajorGroupCnC, NewMajorGroupCnC } from '../major-group-cn-c.model';

export type PartialUpdateMajorGroupCnC = Partial<IMajorGroupCnC> & Pick<IMajorGroupCnC, 'id'>;

export type EntityResponseType = HttpResponse<IMajorGroupCnC>;
export type EntityArrayResponseType = HttpResponse<IMajorGroupCnC[]>;

@Injectable({ providedIn: 'root' })
export class MajorGroupCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/major-group-cn-cs');

  create(majorGroupCnC: NewMajorGroupCnC): Observable<EntityResponseType> {
    return this.http.post<IMajorGroupCnC>(this.resourceUrl, majorGroupCnC, { observe: 'response' });
  }

  update(majorGroupCnC: IMajorGroupCnC): Observable<EntityResponseType> {
    return this.http.put<IMajorGroupCnC>(`${this.resourceUrl}/${this.getMajorGroupCnCIdentifier(majorGroupCnC)}`, majorGroupCnC, {
      observe: 'response',
    });
  }

  partialUpdate(majorGroupCnC: PartialUpdateMajorGroupCnC): Observable<EntityResponseType> {
    return this.http.patch<IMajorGroupCnC>(`${this.resourceUrl}/${this.getMajorGroupCnCIdentifier(majorGroupCnC)}`, majorGroupCnC, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMajorGroupCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMajorGroupCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMajorGroupCnCIdentifier(majorGroupCnC: Pick<IMajorGroupCnC, 'id'>): number {
    return majorGroupCnC.id;
  }

  compareMajorGroupCnC(o1: Pick<IMajorGroupCnC, 'id'> | null, o2: Pick<IMajorGroupCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getMajorGroupCnCIdentifier(o1) === this.getMajorGroupCnCIdentifier(o2) : o1 === o2;
  }

  addMajorGroupCnCToCollectionIfMissing<Type extends Pick<IMajorGroupCnC, 'id'>>(
    majorGroupCnCCollection: Type[],
    ...majorGroupCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const majorGroupCnCS: Type[] = majorGroupCnCSToCheck.filter(isPresent);
    if (majorGroupCnCS.length > 0) {
      const majorGroupCnCCollectionIdentifiers = majorGroupCnCCollection.map(majorGroupCnCItem =>
        this.getMajorGroupCnCIdentifier(majorGroupCnCItem),
      );
      const majorGroupCnCSToAdd = majorGroupCnCS.filter(majorGroupCnCItem => {
        const majorGroupCnCIdentifier = this.getMajorGroupCnCIdentifier(majorGroupCnCItem);
        if (majorGroupCnCCollectionIdentifiers.includes(majorGroupCnCIdentifier)) {
          return false;
        }
        majorGroupCnCCollectionIdentifiers.push(majorGroupCnCIdentifier);
        return true;
      });
      return [...majorGroupCnCSToAdd, ...majorGroupCnCCollection];
    }
    return majorGroupCnCCollection;
  }
}
