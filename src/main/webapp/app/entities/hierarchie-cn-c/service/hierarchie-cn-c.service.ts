import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHierarchieCnC, NewHierarchieCnC } from '../hierarchie-cn-c.model';

export type PartialUpdateHierarchieCnC = Partial<IHierarchieCnC> & Pick<IHierarchieCnC, 'id'>;

export type EntityResponseType = HttpResponse<IHierarchieCnC>;
export type EntityArrayResponseType = HttpResponse<IHierarchieCnC[]>;

@Injectable({ providedIn: 'root' })
export class HierarchieCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hierarchie-cn-cs');

  create(hierarchieCnC: NewHierarchieCnC): Observable<EntityResponseType> {
    return this.http.post<IHierarchieCnC>(this.resourceUrl, hierarchieCnC, { observe: 'response' });
  }

  update(hierarchieCnC: IHierarchieCnC): Observable<EntityResponseType> {
    return this.http.put<IHierarchieCnC>(`${this.resourceUrl}/${this.getHierarchieCnCIdentifier(hierarchieCnC)}`, hierarchieCnC, {
      observe: 'response',
    });
  }

  partialUpdate(hierarchieCnC: PartialUpdateHierarchieCnC): Observable<EntityResponseType> {
    return this.http.patch<IHierarchieCnC>(`${this.resourceUrl}/${this.getHierarchieCnCIdentifier(hierarchieCnC)}`, hierarchieCnC, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHierarchieCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHierarchieCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHierarchieCnCIdentifier(hierarchieCnC: Pick<IHierarchieCnC, 'id'>): number {
    return hierarchieCnC.id;
  }

  compareHierarchieCnC(o1: Pick<IHierarchieCnC, 'id'> | null, o2: Pick<IHierarchieCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getHierarchieCnCIdentifier(o1) === this.getHierarchieCnCIdentifier(o2) : o1 === o2;
  }

  addHierarchieCnCToCollectionIfMissing<Type extends Pick<IHierarchieCnC, 'id'>>(
    hierarchieCnCCollection: Type[],
    ...hierarchieCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hierarchieCnCS: Type[] = hierarchieCnCSToCheck.filter(isPresent);
    if (hierarchieCnCS.length > 0) {
      const hierarchieCnCCollectionIdentifiers = hierarchieCnCCollection.map(hierarchieCnCItem =>
        this.getHierarchieCnCIdentifier(hierarchieCnCItem),
      );
      const hierarchieCnCSToAdd = hierarchieCnCS.filter(hierarchieCnCItem => {
        const hierarchieCnCIdentifier = this.getHierarchieCnCIdentifier(hierarchieCnCItem);
        if (hierarchieCnCCollectionIdentifiers.includes(hierarchieCnCIdentifier)) {
          return false;
        }
        hierarchieCnCCollectionIdentifiers.push(hierarchieCnCIdentifier);
        return true;
      });
      return [...hierarchieCnCSToAdd, ...hierarchieCnCCollection];
    }
    return hierarchieCnCCollection;
  }
}
