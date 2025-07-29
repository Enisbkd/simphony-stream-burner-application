import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocieteTrans, NewSocieteTrans } from '../societe-trans.model';

export type PartialUpdateSocieteTrans = Partial<ISocieteTrans> & Pick<ISocieteTrans, 'id'>;

export type EntityResponseType = HttpResponse<ISocieteTrans>;
export type EntityArrayResponseType = HttpResponse<ISocieteTrans[]>;

@Injectable({ providedIn: 'root' })
export class SocieteTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/societe-trans');

  create(societeTrans: NewSocieteTrans): Observable<EntityResponseType> {
    return this.http.post<ISocieteTrans>(this.resourceUrl, societeTrans, { observe: 'response' });
  }

  update(societeTrans: ISocieteTrans): Observable<EntityResponseType> {
    return this.http.put<ISocieteTrans>(`${this.resourceUrl}/${this.getSocieteTransIdentifier(societeTrans)}`, societeTrans, {
      observe: 'response',
    });
  }

  partialUpdate(societeTrans: PartialUpdateSocieteTrans): Observable<EntityResponseType> {
    return this.http.patch<ISocieteTrans>(`${this.resourceUrl}/${this.getSocieteTransIdentifier(societeTrans)}`, societeTrans, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISocieteTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocieteTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocieteTransIdentifier(societeTrans: Pick<ISocieteTrans, 'id'>): number {
    return societeTrans.id;
  }

  compareSocieteTrans(o1: Pick<ISocieteTrans, 'id'> | null, o2: Pick<ISocieteTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocieteTransIdentifier(o1) === this.getSocieteTransIdentifier(o2) : o1 === o2;
  }

  addSocieteTransToCollectionIfMissing<Type extends Pick<ISocieteTrans, 'id'>>(
    societeTransCollection: Type[],
    ...societeTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societeTrans: Type[] = societeTransToCheck.filter(isPresent);
    if (societeTrans.length > 0) {
      const societeTransCollectionIdentifiers = societeTransCollection.map(societeTransItem =>
        this.getSocieteTransIdentifier(societeTransItem),
      );
      const societeTransToAdd = societeTrans.filter(societeTransItem => {
        const societeTransIdentifier = this.getSocieteTransIdentifier(societeTransItem);
        if (societeTransCollectionIdentifiers.includes(societeTransIdentifier)) {
          return false;
        }
        societeTransCollectionIdentifiers.push(societeTransIdentifier);
        return true;
      });
      return [...societeTransToAdd, ...societeTransCollection];
    }
    return societeTransCollection;
  }
}
