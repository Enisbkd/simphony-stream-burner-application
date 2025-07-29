import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRemiseTrans, NewRemiseTrans } from '../remise-trans.model';

export type PartialUpdateRemiseTrans = Partial<IRemiseTrans> & Pick<IRemiseTrans, 'id'>;

export type EntityResponseType = HttpResponse<IRemiseTrans>;
export type EntityArrayResponseType = HttpResponse<IRemiseTrans[]>;

@Injectable({ providedIn: 'root' })
export class RemiseTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/remise-trans');

  create(remiseTrans: NewRemiseTrans): Observable<EntityResponseType> {
    return this.http.post<IRemiseTrans>(this.resourceUrl, remiseTrans, { observe: 'response' });
  }

  update(remiseTrans: IRemiseTrans): Observable<EntityResponseType> {
    return this.http.put<IRemiseTrans>(`${this.resourceUrl}/${this.getRemiseTransIdentifier(remiseTrans)}`, remiseTrans, {
      observe: 'response',
    });
  }

  partialUpdate(remiseTrans: PartialUpdateRemiseTrans): Observable<EntityResponseType> {
    return this.http.patch<IRemiseTrans>(`${this.resourceUrl}/${this.getRemiseTransIdentifier(remiseTrans)}`, remiseTrans, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRemiseTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRemiseTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRemiseTransIdentifier(remiseTrans: Pick<IRemiseTrans, 'id'>): number {
    return remiseTrans.id;
  }

  compareRemiseTrans(o1: Pick<IRemiseTrans, 'id'> | null, o2: Pick<IRemiseTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getRemiseTransIdentifier(o1) === this.getRemiseTransIdentifier(o2) : o1 === o2;
  }

  addRemiseTransToCollectionIfMissing<Type extends Pick<IRemiseTrans, 'id'>>(
    remiseTransCollection: Type[],
    ...remiseTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const remiseTrans: Type[] = remiseTransToCheck.filter(isPresent);
    if (remiseTrans.length > 0) {
      const remiseTransCollectionIdentifiers = remiseTransCollection.map(remiseTransItem => this.getRemiseTransIdentifier(remiseTransItem));
      const remiseTransToAdd = remiseTrans.filter(remiseTransItem => {
        const remiseTransIdentifier = this.getRemiseTransIdentifier(remiseTransItem);
        if (remiseTransCollectionIdentifiers.includes(remiseTransIdentifier)) {
          return false;
        }
        remiseTransCollectionIdentifiers.push(remiseTransIdentifier);
        return true;
      });
      return [...remiseTransToAdd, ...remiseTransCollection];
    }
    return remiseTransCollection;
  }
}
