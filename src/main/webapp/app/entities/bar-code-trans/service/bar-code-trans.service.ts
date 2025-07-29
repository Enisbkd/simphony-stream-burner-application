import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBarCodeTrans, NewBarCodeTrans } from '../bar-code-trans.model';

export type PartialUpdateBarCodeTrans = Partial<IBarCodeTrans> & Pick<IBarCodeTrans, 'id'>;

export type EntityResponseType = HttpResponse<IBarCodeTrans>;
export type EntityArrayResponseType = HttpResponse<IBarCodeTrans[]>;

@Injectable({ providedIn: 'root' })
export class BarCodeTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bar-code-trans');

  create(barCodeTrans: NewBarCodeTrans): Observable<EntityResponseType> {
    return this.http.post<IBarCodeTrans>(this.resourceUrl, barCodeTrans, { observe: 'response' });
  }

  update(barCodeTrans: IBarCodeTrans): Observable<EntityResponseType> {
    return this.http.put<IBarCodeTrans>(`${this.resourceUrl}/${this.getBarCodeTransIdentifier(barCodeTrans)}`, barCodeTrans, {
      observe: 'response',
    });
  }

  partialUpdate(barCodeTrans: PartialUpdateBarCodeTrans): Observable<EntityResponseType> {
    return this.http.patch<IBarCodeTrans>(`${this.resourceUrl}/${this.getBarCodeTransIdentifier(barCodeTrans)}`, barCodeTrans, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBarCodeTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarCodeTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBarCodeTransIdentifier(barCodeTrans: Pick<IBarCodeTrans, 'id'>): number {
    return barCodeTrans.id;
  }

  compareBarCodeTrans(o1: Pick<IBarCodeTrans, 'id'> | null, o2: Pick<IBarCodeTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getBarCodeTransIdentifier(o1) === this.getBarCodeTransIdentifier(o2) : o1 === o2;
  }

  addBarCodeTransToCollectionIfMissing<Type extends Pick<IBarCodeTrans, 'id'>>(
    barCodeTransCollection: Type[],
    ...barCodeTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const barCodeTrans: Type[] = barCodeTransToCheck.filter(isPresent);
    if (barCodeTrans.length > 0) {
      const barCodeTransCollectionIdentifiers = barCodeTransCollection.map(barCodeTransItem =>
        this.getBarCodeTransIdentifier(barCodeTransItem),
      );
      const barCodeTransToAdd = barCodeTrans.filter(barCodeTransItem => {
        const barCodeTransIdentifier = this.getBarCodeTransIdentifier(barCodeTransItem);
        if (barCodeTransCollectionIdentifiers.includes(barCodeTransIdentifier)) {
          return false;
        }
        barCodeTransCollectionIdentifiers.push(barCodeTransIdentifier);
        return true;
      });
      return [...barCodeTransToAdd, ...barCodeTransCollection];
    }
    return barCodeTransCollection;
  }
}
