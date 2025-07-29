import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModePaiementTrans, NewModePaiementTrans } from '../mode-paiement-trans.model';

export type PartialUpdateModePaiementTrans = Partial<IModePaiementTrans> & Pick<IModePaiementTrans, 'id'>;

export type EntityResponseType = HttpResponse<IModePaiementTrans>;
export type EntityArrayResponseType = HttpResponse<IModePaiementTrans[]>;

@Injectable({ providedIn: 'root' })
export class ModePaiementTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mode-paiement-trans');

  create(modePaiementTrans: NewModePaiementTrans): Observable<EntityResponseType> {
    return this.http.post<IModePaiementTrans>(this.resourceUrl, modePaiementTrans, { observe: 'response' });
  }

  update(modePaiementTrans: IModePaiementTrans): Observable<EntityResponseType> {
    return this.http.put<IModePaiementTrans>(
      `${this.resourceUrl}/${this.getModePaiementTransIdentifier(modePaiementTrans)}`,
      modePaiementTrans,
      { observe: 'response' },
    );
  }

  partialUpdate(modePaiementTrans: PartialUpdateModePaiementTrans): Observable<EntityResponseType> {
    return this.http.patch<IModePaiementTrans>(
      `${this.resourceUrl}/${this.getModePaiementTransIdentifier(modePaiementTrans)}`,
      modePaiementTrans,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModePaiementTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModePaiementTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getModePaiementTransIdentifier(modePaiementTrans: Pick<IModePaiementTrans, 'id'>): number {
    return modePaiementTrans.id;
  }

  compareModePaiementTrans(o1: Pick<IModePaiementTrans, 'id'> | null, o2: Pick<IModePaiementTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getModePaiementTransIdentifier(o1) === this.getModePaiementTransIdentifier(o2) : o1 === o2;
  }

  addModePaiementTransToCollectionIfMissing<Type extends Pick<IModePaiementTrans, 'id'>>(
    modePaiementTransCollection: Type[],
    ...modePaiementTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const modePaiementTrans: Type[] = modePaiementTransToCheck.filter(isPresent);
    if (modePaiementTrans.length > 0) {
      const modePaiementTransCollectionIdentifiers = modePaiementTransCollection.map(modePaiementTransItem =>
        this.getModePaiementTransIdentifier(modePaiementTransItem),
      );
      const modePaiementTransToAdd = modePaiementTrans.filter(modePaiementTransItem => {
        const modePaiementTransIdentifier = this.getModePaiementTransIdentifier(modePaiementTransItem);
        if (modePaiementTransCollectionIdentifiers.includes(modePaiementTransIdentifier)) {
          return false;
        }
        modePaiementTransCollectionIdentifiers.push(modePaiementTransIdentifier);
        return true;
      });
      return [...modePaiementTransToAdd, ...modePaiementTransCollection];
    }
    return modePaiementTransCollection;
  }
}
