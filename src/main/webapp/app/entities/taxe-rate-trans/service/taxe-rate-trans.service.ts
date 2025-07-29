import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaxeRateTrans, NewTaxeRateTrans } from '../taxe-rate-trans.model';

export type PartialUpdateTaxeRateTrans = Partial<ITaxeRateTrans> & Pick<ITaxeRateTrans, 'id'>;

export type EntityResponseType = HttpResponse<ITaxeRateTrans>;
export type EntityArrayResponseType = HttpResponse<ITaxeRateTrans[]>;

@Injectable({ providedIn: 'root' })
export class TaxeRateTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/taxe-rate-trans');

  create(taxeRateTrans: NewTaxeRateTrans): Observable<EntityResponseType> {
    return this.http.post<ITaxeRateTrans>(this.resourceUrl, taxeRateTrans, { observe: 'response' });
  }

  update(taxeRateTrans: ITaxeRateTrans): Observable<EntityResponseType> {
    return this.http.put<ITaxeRateTrans>(`${this.resourceUrl}/${this.getTaxeRateTransIdentifier(taxeRateTrans)}`, taxeRateTrans, {
      observe: 'response',
    });
  }

  partialUpdate(taxeRateTrans: PartialUpdateTaxeRateTrans): Observable<EntityResponseType> {
    return this.http.patch<ITaxeRateTrans>(`${this.resourceUrl}/${this.getTaxeRateTransIdentifier(taxeRateTrans)}`, taxeRateTrans, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxeRateTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxeRateTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTaxeRateTransIdentifier(taxeRateTrans: Pick<ITaxeRateTrans, 'id'>): number {
    return taxeRateTrans.id;
  }

  compareTaxeRateTrans(o1: Pick<ITaxeRateTrans, 'id'> | null, o2: Pick<ITaxeRateTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getTaxeRateTransIdentifier(o1) === this.getTaxeRateTransIdentifier(o2) : o1 === o2;
  }

  addTaxeRateTransToCollectionIfMissing<Type extends Pick<ITaxeRateTrans, 'id'>>(
    taxeRateTransCollection: Type[],
    ...taxeRateTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const taxeRateTrans: Type[] = taxeRateTransToCheck.filter(isPresent);
    if (taxeRateTrans.length > 0) {
      const taxeRateTransCollectionIdentifiers = taxeRateTransCollection.map(taxeRateTransItem =>
        this.getTaxeRateTransIdentifier(taxeRateTransItem),
      );
      const taxeRateTransToAdd = taxeRateTrans.filter(taxeRateTransItem => {
        const taxeRateTransIdentifier = this.getTaxeRateTransIdentifier(taxeRateTransItem);
        if (taxeRateTransCollectionIdentifiers.includes(taxeRateTransIdentifier)) {
          return false;
        }
        taxeRateTransCollectionIdentifiers.push(taxeRateTransIdentifier);
        return true;
      });
      return [...taxeRateTransToAdd, ...taxeRateTransCollection];
    }
    return taxeRateTransCollection;
  }
}
