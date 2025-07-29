import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderTypeBI, NewOrderTypeBI } from '../order-type-bi.model';

export type PartialUpdateOrderTypeBI = Partial<IOrderTypeBI> & Pick<IOrderTypeBI, 'id'>;

export type EntityResponseType = HttpResponse<IOrderTypeBI>;
export type EntityArrayResponseType = HttpResponse<IOrderTypeBI[]>;

@Injectable({ providedIn: 'root' })
export class OrderTypeBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-type-bis');

  create(orderTypeBI: NewOrderTypeBI): Observable<EntityResponseType> {
    return this.http.post<IOrderTypeBI>(this.resourceUrl, orderTypeBI, { observe: 'response' });
  }

  update(orderTypeBI: IOrderTypeBI): Observable<EntityResponseType> {
    return this.http.put<IOrderTypeBI>(`${this.resourceUrl}/${this.getOrderTypeBIIdentifier(orderTypeBI)}`, orderTypeBI, {
      observe: 'response',
    });
  }

  partialUpdate(orderTypeBI: PartialUpdateOrderTypeBI): Observable<EntityResponseType> {
    return this.http.patch<IOrderTypeBI>(`${this.resourceUrl}/${this.getOrderTypeBIIdentifier(orderTypeBI)}`, orderTypeBI, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderTypeBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderTypeBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrderTypeBIIdentifier(orderTypeBI: Pick<IOrderTypeBI, 'id'>): number {
    return orderTypeBI.id;
  }

  compareOrderTypeBI(o1: Pick<IOrderTypeBI, 'id'> | null, o2: Pick<IOrderTypeBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrderTypeBIIdentifier(o1) === this.getOrderTypeBIIdentifier(o2) : o1 === o2;
  }

  addOrderTypeBIToCollectionIfMissing<Type extends Pick<IOrderTypeBI, 'id'>>(
    orderTypeBICollection: Type[],
    ...orderTypeBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orderTypeBIS: Type[] = orderTypeBISToCheck.filter(isPresent);
    if (orderTypeBIS.length > 0) {
      const orderTypeBICollectionIdentifiers = orderTypeBICollection.map(orderTypeBIItem => this.getOrderTypeBIIdentifier(orderTypeBIItem));
      const orderTypeBISToAdd = orderTypeBIS.filter(orderTypeBIItem => {
        const orderTypeBIIdentifier = this.getOrderTypeBIIdentifier(orderTypeBIItem);
        if (orderTypeBICollectionIdentifiers.includes(orderTypeBIIdentifier)) {
          return false;
        }
        orderTypeBICollectionIdentifiers.push(orderTypeBIIdentifier);
        return true;
      });
      return [...orderTypeBISToAdd, ...orderTypeBICollection];
    }
    return orderTypeBICollection;
  }
}
