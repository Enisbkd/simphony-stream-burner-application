import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaxeBI, NewTaxeBI } from '../taxe-bi.model';

export type PartialUpdateTaxeBI = Partial<ITaxeBI> & Pick<ITaxeBI, 'id'>;

export type EntityResponseType = HttpResponse<ITaxeBI>;
export type EntityArrayResponseType = HttpResponse<ITaxeBI[]>;

@Injectable({ providedIn: 'root' })
export class TaxeBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/taxe-bis');

  create(taxeBI: NewTaxeBI): Observable<EntityResponseType> {
    return this.http.post<ITaxeBI>(this.resourceUrl, taxeBI, { observe: 'response' });
  }

  update(taxeBI: ITaxeBI): Observable<EntityResponseType> {
    return this.http.put<ITaxeBI>(`${this.resourceUrl}/${this.getTaxeBIIdentifier(taxeBI)}`, taxeBI, { observe: 'response' });
  }

  partialUpdate(taxeBI: PartialUpdateTaxeBI): Observable<EntityResponseType> {
    return this.http.patch<ITaxeBI>(`${this.resourceUrl}/${this.getTaxeBIIdentifier(taxeBI)}`, taxeBI, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxeBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxeBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTaxeBIIdentifier(taxeBI: Pick<ITaxeBI, 'id'>): number {
    return taxeBI.id;
  }

  compareTaxeBI(o1: Pick<ITaxeBI, 'id'> | null, o2: Pick<ITaxeBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getTaxeBIIdentifier(o1) === this.getTaxeBIIdentifier(o2) : o1 === o2;
  }

  addTaxeBIToCollectionIfMissing<Type extends Pick<ITaxeBI, 'id'>>(
    taxeBICollection: Type[],
    ...taxeBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const taxeBIS: Type[] = taxeBISToCheck.filter(isPresent);
    if (taxeBIS.length > 0) {
      const taxeBICollectionIdentifiers = taxeBICollection.map(taxeBIItem => this.getTaxeBIIdentifier(taxeBIItem));
      const taxeBISToAdd = taxeBIS.filter(taxeBIItem => {
        const taxeBIIdentifier = this.getTaxeBIIdentifier(taxeBIItem);
        if (taxeBICollectionIdentifiers.includes(taxeBIIdentifier)) {
          return false;
        }
        taxeBICollectionIdentifiers.push(taxeBIIdentifier);
        return true;
      });
      return [...taxeBISToAdd, ...taxeBICollection];
    }
    return taxeBICollection;
  }
}
