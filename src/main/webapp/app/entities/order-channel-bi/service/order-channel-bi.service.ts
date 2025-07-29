import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderChannelBI, NewOrderChannelBI } from '../order-channel-bi.model';

export type PartialUpdateOrderChannelBI = Partial<IOrderChannelBI> & Pick<IOrderChannelBI, 'id'>;

export type EntityResponseType = HttpResponse<IOrderChannelBI>;
export type EntityArrayResponseType = HttpResponse<IOrderChannelBI[]>;

@Injectable({ providedIn: 'root' })
export class OrderChannelBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-channel-bis');

  create(orderChannelBI: NewOrderChannelBI): Observable<EntityResponseType> {
    return this.http.post<IOrderChannelBI>(this.resourceUrl, orderChannelBI, { observe: 'response' });
  }

  update(orderChannelBI: IOrderChannelBI): Observable<EntityResponseType> {
    return this.http.put<IOrderChannelBI>(`${this.resourceUrl}/${this.getOrderChannelBIIdentifier(orderChannelBI)}`, orderChannelBI, {
      observe: 'response',
    });
  }

  partialUpdate(orderChannelBI: PartialUpdateOrderChannelBI): Observable<EntityResponseType> {
    return this.http.patch<IOrderChannelBI>(`${this.resourceUrl}/${this.getOrderChannelBIIdentifier(orderChannelBI)}`, orderChannelBI, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderChannelBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderChannelBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrderChannelBIIdentifier(orderChannelBI: Pick<IOrderChannelBI, 'id'>): number {
    return orderChannelBI.id;
  }

  compareOrderChannelBI(o1: Pick<IOrderChannelBI, 'id'> | null, o2: Pick<IOrderChannelBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrderChannelBIIdentifier(o1) === this.getOrderChannelBIIdentifier(o2) : o1 === o2;
  }

  addOrderChannelBIToCollectionIfMissing<Type extends Pick<IOrderChannelBI, 'id'>>(
    orderChannelBICollection: Type[],
    ...orderChannelBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orderChannelBIS: Type[] = orderChannelBISToCheck.filter(isPresent);
    if (orderChannelBIS.length > 0) {
      const orderChannelBICollectionIdentifiers = orderChannelBICollection.map(orderChannelBIItem =>
        this.getOrderChannelBIIdentifier(orderChannelBIItem),
      );
      const orderChannelBISToAdd = orderChannelBIS.filter(orderChannelBIItem => {
        const orderChannelBIIdentifier = this.getOrderChannelBIIdentifier(orderChannelBIItem);
        if (orderChannelBICollectionIdentifiers.includes(orderChannelBIIdentifier)) {
          return false;
        }
        orderChannelBICollectionIdentifiers.push(orderChannelBIIdentifier);
        return true;
      });
      return [...orderChannelBISToAdd, ...orderChannelBICollection];
    }
    return orderChannelBICollection;
  }
}
