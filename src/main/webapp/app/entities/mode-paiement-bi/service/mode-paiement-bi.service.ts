import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModePaiementBI, NewModePaiementBI } from '../mode-paiement-bi.model';

export type PartialUpdateModePaiementBI = Partial<IModePaiementBI> & Pick<IModePaiementBI, 'id'>;

export type EntityResponseType = HttpResponse<IModePaiementBI>;
export type EntityArrayResponseType = HttpResponse<IModePaiementBI[]>;

@Injectable({ providedIn: 'root' })
export class ModePaiementBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mode-paiement-bis');

  create(modePaiementBI: NewModePaiementBI): Observable<EntityResponseType> {
    return this.http.post<IModePaiementBI>(this.resourceUrl, modePaiementBI, { observe: 'response' });
  }

  update(modePaiementBI: IModePaiementBI): Observable<EntityResponseType> {
    return this.http.put<IModePaiementBI>(`${this.resourceUrl}/${this.getModePaiementBIIdentifier(modePaiementBI)}`, modePaiementBI, {
      observe: 'response',
    });
  }

  partialUpdate(modePaiementBI: PartialUpdateModePaiementBI): Observable<EntityResponseType> {
    return this.http.patch<IModePaiementBI>(`${this.resourceUrl}/${this.getModePaiementBIIdentifier(modePaiementBI)}`, modePaiementBI, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModePaiementBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModePaiementBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getModePaiementBIIdentifier(modePaiementBI: Pick<IModePaiementBI, 'id'>): number {
    return modePaiementBI.id;
  }

  compareModePaiementBI(o1: Pick<IModePaiementBI, 'id'> | null, o2: Pick<IModePaiementBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getModePaiementBIIdentifier(o1) === this.getModePaiementBIIdentifier(o2) : o1 === o2;
  }

  addModePaiementBIToCollectionIfMissing<Type extends Pick<IModePaiementBI, 'id'>>(
    modePaiementBICollection: Type[],
    ...modePaiementBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const modePaiementBIS: Type[] = modePaiementBISToCheck.filter(isPresent);
    if (modePaiementBIS.length > 0) {
      const modePaiementBICollectionIdentifiers = modePaiementBICollection.map(modePaiementBIItem =>
        this.getModePaiementBIIdentifier(modePaiementBIItem),
      );
      const modePaiementBISToAdd = modePaiementBIS.filter(modePaiementBIItem => {
        const modePaiementBIIdentifier = this.getModePaiementBIIdentifier(modePaiementBIItem);
        if (modePaiementBICollectionIdentifiers.includes(modePaiementBIIdentifier)) {
          return false;
        }
        modePaiementBICollectionIdentifiers.push(modePaiementBIIdentifier);
        return true;
      });
      return [...modePaiementBISToAdd, ...modePaiementBICollection];
    }
    return modePaiementBICollection;
  }
}
