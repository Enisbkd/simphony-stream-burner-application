import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFamilyGroupCnC, NewFamilyGroupCnC } from '../family-group-cn-c.model';

export type PartialUpdateFamilyGroupCnC = Partial<IFamilyGroupCnC> & Pick<IFamilyGroupCnC, 'id'>;

export type EntityResponseType = HttpResponse<IFamilyGroupCnC>;
export type EntityArrayResponseType = HttpResponse<IFamilyGroupCnC[]>;

@Injectable({ providedIn: 'root' })
export class FamilyGroupCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/family-group-cn-cs');

  create(familyGroupCnC: NewFamilyGroupCnC): Observable<EntityResponseType> {
    return this.http.post<IFamilyGroupCnC>(this.resourceUrl, familyGroupCnC, { observe: 'response' });
  }

  update(familyGroupCnC: IFamilyGroupCnC): Observable<EntityResponseType> {
    return this.http.put<IFamilyGroupCnC>(`${this.resourceUrl}/${this.getFamilyGroupCnCIdentifier(familyGroupCnC)}`, familyGroupCnC, {
      observe: 'response',
    });
  }

  partialUpdate(familyGroupCnC: PartialUpdateFamilyGroupCnC): Observable<EntityResponseType> {
    return this.http.patch<IFamilyGroupCnC>(`${this.resourceUrl}/${this.getFamilyGroupCnCIdentifier(familyGroupCnC)}`, familyGroupCnC, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFamilyGroupCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilyGroupCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFamilyGroupCnCIdentifier(familyGroupCnC: Pick<IFamilyGroupCnC, 'id'>): number {
    return familyGroupCnC.id;
  }

  compareFamilyGroupCnC(o1: Pick<IFamilyGroupCnC, 'id'> | null, o2: Pick<IFamilyGroupCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getFamilyGroupCnCIdentifier(o1) === this.getFamilyGroupCnCIdentifier(o2) : o1 === o2;
  }

  addFamilyGroupCnCToCollectionIfMissing<Type extends Pick<IFamilyGroupCnC, 'id'>>(
    familyGroupCnCCollection: Type[],
    ...familyGroupCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const familyGroupCnCS: Type[] = familyGroupCnCSToCheck.filter(isPresent);
    if (familyGroupCnCS.length > 0) {
      const familyGroupCnCCollectionIdentifiers = familyGroupCnCCollection.map(familyGroupCnCItem =>
        this.getFamilyGroupCnCIdentifier(familyGroupCnCItem),
      );
      const familyGroupCnCSToAdd = familyGroupCnCS.filter(familyGroupCnCItem => {
        const familyGroupCnCIdentifier = this.getFamilyGroupCnCIdentifier(familyGroupCnCItem);
        if (familyGroupCnCCollectionIdentifiers.includes(familyGroupCnCIdentifier)) {
          return false;
        }
        familyGroupCnCCollectionIdentifiers.push(familyGroupCnCIdentifier);
        return true;
      });
      return [...familyGroupCnCSToAdd, ...familyGroupCnCCollection];
    }
    return familyGroupCnCCollection;
  }
}
