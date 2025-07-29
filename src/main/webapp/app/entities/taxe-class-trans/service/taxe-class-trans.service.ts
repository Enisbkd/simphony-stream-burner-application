import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaxeClassTrans, NewTaxeClassTrans } from '../taxe-class-trans.model';

export type PartialUpdateTaxeClassTrans = Partial<ITaxeClassTrans> & Pick<ITaxeClassTrans, 'id'>;

export type EntityResponseType = HttpResponse<ITaxeClassTrans>;
export type EntityArrayResponseType = HttpResponse<ITaxeClassTrans[]>;

@Injectable({ providedIn: 'root' })
export class TaxeClassTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/taxe-class-trans');

  create(taxeClassTrans: NewTaxeClassTrans): Observable<EntityResponseType> {
    return this.http.post<ITaxeClassTrans>(this.resourceUrl, taxeClassTrans, { observe: 'response' });
  }

  update(taxeClassTrans: ITaxeClassTrans): Observable<EntityResponseType> {
    return this.http.put<ITaxeClassTrans>(`${this.resourceUrl}/${this.getTaxeClassTransIdentifier(taxeClassTrans)}`, taxeClassTrans, {
      observe: 'response',
    });
  }

  partialUpdate(taxeClassTrans: PartialUpdateTaxeClassTrans): Observable<EntityResponseType> {
    return this.http.patch<ITaxeClassTrans>(`${this.resourceUrl}/${this.getTaxeClassTransIdentifier(taxeClassTrans)}`, taxeClassTrans, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxeClassTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxeClassTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTaxeClassTransIdentifier(taxeClassTrans: Pick<ITaxeClassTrans, 'id'>): number {
    return taxeClassTrans.id;
  }

  compareTaxeClassTrans(o1: Pick<ITaxeClassTrans, 'id'> | null, o2: Pick<ITaxeClassTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getTaxeClassTransIdentifier(o1) === this.getTaxeClassTransIdentifier(o2) : o1 === o2;
  }

  addTaxeClassTransToCollectionIfMissing<Type extends Pick<ITaxeClassTrans, 'id'>>(
    taxeClassTransCollection: Type[],
    ...taxeClassTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const taxeClassTrans: Type[] = taxeClassTransToCheck.filter(isPresent);
    if (taxeClassTrans.length > 0) {
      const taxeClassTransCollectionIdentifiers = taxeClassTransCollection.map(taxeClassTransItem =>
        this.getTaxeClassTransIdentifier(taxeClassTransItem),
      );
      const taxeClassTransToAdd = taxeClassTrans.filter(taxeClassTransItem => {
        const taxeClassTransIdentifier = this.getTaxeClassTransIdentifier(taxeClassTransItem);
        if (taxeClassTransCollectionIdentifiers.includes(taxeClassTransIdentifier)) {
          return false;
        }
        taxeClassTransCollectionIdentifiers.push(taxeClassTransIdentifier);
        return true;
      });
      return [...taxeClassTransToAdd, ...taxeClassTransCollection];
    }
    return taxeClassTransCollection;
  }
}
