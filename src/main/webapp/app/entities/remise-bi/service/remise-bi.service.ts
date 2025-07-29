import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRemiseBI, NewRemiseBI } from '../remise-bi.model';

export type PartialUpdateRemiseBI = Partial<IRemiseBI> & Pick<IRemiseBI, 'id'>;

export type EntityResponseType = HttpResponse<IRemiseBI>;
export type EntityArrayResponseType = HttpResponse<IRemiseBI[]>;

@Injectable({ providedIn: 'root' })
export class RemiseBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/remise-bis');

  create(remiseBI: NewRemiseBI): Observable<EntityResponseType> {
    return this.http.post<IRemiseBI>(this.resourceUrl, remiseBI, { observe: 'response' });
  }

  update(remiseBI: IRemiseBI): Observable<EntityResponseType> {
    return this.http.put<IRemiseBI>(`${this.resourceUrl}/${this.getRemiseBIIdentifier(remiseBI)}`, remiseBI, { observe: 'response' });
  }

  partialUpdate(remiseBI: PartialUpdateRemiseBI): Observable<EntityResponseType> {
    return this.http.patch<IRemiseBI>(`${this.resourceUrl}/${this.getRemiseBIIdentifier(remiseBI)}`, remiseBI, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRemiseBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRemiseBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRemiseBIIdentifier(remiseBI: Pick<IRemiseBI, 'id'>): number {
    return remiseBI.id;
  }

  compareRemiseBI(o1: Pick<IRemiseBI, 'id'> | null, o2: Pick<IRemiseBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getRemiseBIIdentifier(o1) === this.getRemiseBIIdentifier(o2) : o1 === o2;
  }

  addRemiseBIToCollectionIfMissing<Type extends Pick<IRemiseBI, 'id'>>(
    remiseBICollection: Type[],
    ...remiseBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const remiseBIS: Type[] = remiseBISToCheck.filter(isPresent);
    if (remiseBIS.length > 0) {
      const remiseBICollectionIdentifiers = remiseBICollection.map(remiseBIItem => this.getRemiseBIIdentifier(remiseBIItem));
      const remiseBISToAdd = remiseBIS.filter(remiseBIItem => {
        const remiseBIIdentifier = this.getRemiseBIIdentifier(remiseBIItem);
        if (remiseBICollectionIdentifiers.includes(remiseBIIdentifier)) {
          return false;
        }
        remiseBICollectionIdentifiers.push(remiseBIIdentifier);
        return true;
      });
      return [...remiseBISToAdd, ...remiseBICollection];
    }
    return remiseBICollection;
  }
}
