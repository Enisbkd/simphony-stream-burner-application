import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderType, NewOrderType } from '../order-type.model';

export type PartialUpdateOrderType = Partial<IOrderType> & Pick<IOrderType, 'id'>;

export type EntityResponseType = HttpResponse<IOrderType>;
export type EntityArrayResponseType = HttpResponse<IOrderType[]>;

@Injectable({ providedIn: 'root' })
export class OrderTypeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-types');

  create(orderType: NewOrderType): Observable<EntityResponseType> {
    return this.http.post<IOrderType>(this.resourceUrl, orderType, { observe: 'response' });
  }

  update(orderType: IOrderType): Observable<EntityResponseType> {
    return this.http.put<IOrderType>(`${this.resourceUrl}/${this.getOrderTypeIdentifier(orderType)}`, orderType, { observe: 'response' });
  }

  partialUpdate(orderType: PartialUpdateOrderType): Observable<EntityResponseType> {
    return this.http.patch<IOrderType>(`${this.resourceUrl}/${this.getOrderTypeIdentifier(orderType)}`, orderType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrderTypeIdentifier(orderType: Pick<IOrderType, 'id'>): number {
    return orderType.id;
  }

  compareOrderType(o1: Pick<IOrderType, 'id'> | null, o2: Pick<IOrderType, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrderTypeIdentifier(o1) === this.getOrderTypeIdentifier(o2) : o1 === o2;
  }

  addOrderTypeToCollectionIfMissing<Type extends Pick<IOrderType, 'id'>>(
    orderTypeCollection: Type[],
    ...orderTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orderTypes: Type[] = orderTypesToCheck.filter(isPresent);
    if (orderTypes.length > 0) {
      const orderTypeCollectionIdentifiers = orderTypeCollection.map(orderTypeItem => this.getOrderTypeIdentifier(orderTypeItem));
      const orderTypesToAdd = orderTypes.filter(orderTypeItem => {
        const orderTypeIdentifier = this.getOrderTypeIdentifier(orderTypeItem);
        if (orderTypeCollectionIdentifiers.includes(orderTypeIdentifier)) {
          return false;
        }
        orderTypeCollectionIdentifiers.push(orderTypeIdentifier);
        return true;
      });
      return [...orderTypesToAdd, ...orderTypeCollection];
    }
    return orderTypeCollection;
  }
}
