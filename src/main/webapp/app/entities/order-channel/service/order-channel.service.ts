import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderChannel, NewOrderChannel } from '../order-channel.model';

export type PartialUpdateOrderChannel = Partial<IOrderChannel> & Pick<IOrderChannel, 'id'>;

export type EntityResponseType = HttpResponse<IOrderChannel>;
export type EntityArrayResponseType = HttpResponse<IOrderChannel[]>;

@Injectable({ providedIn: 'root' })
export class OrderChannelService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-channels');

  create(orderChannel: NewOrderChannel): Observable<EntityResponseType> {
    return this.http.post<IOrderChannel>(this.resourceUrl, orderChannel, { observe: 'response' });
  }

  update(orderChannel: IOrderChannel): Observable<EntityResponseType> {
    return this.http.put<IOrderChannel>(`${this.resourceUrl}/${this.getOrderChannelIdentifier(orderChannel)}`, orderChannel, {
      observe: 'response',
    });
  }

  partialUpdate(orderChannel: PartialUpdateOrderChannel): Observable<EntityResponseType> {
    return this.http.patch<IOrderChannel>(`${this.resourceUrl}/${this.getOrderChannelIdentifier(orderChannel)}`, orderChannel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrderChannelIdentifier(orderChannel: Pick<IOrderChannel, 'id'>): number {
    return orderChannel.id;
  }

  compareOrderChannel(o1: Pick<IOrderChannel, 'id'> | null, o2: Pick<IOrderChannel, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrderChannelIdentifier(o1) === this.getOrderChannelIdentifier(o2) : o1 === o2;
  }

  addOrderChannelToCollectionIfMissing<Type extends Pick<IOrderChannel, 'id'>>(
    orderChannelCollection: Type[],
    ...orderChannelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orderChannels: Type[] = orderChannelsToCheck.filter(isPresent);
    if (orderChannels.length > 0) {
      const orderChannelCollectionIdentifiers = orderChannelCollection.map(orderChannelItem =>
        this.getOrderChannelIdentifier(orderChannelItem),
      );
      const orderChannelsToAdd = orderChannels.filter(orderChannelItem => {
        const orderChannelIdentifier = this.getOrderChannelIdentifier(orderChannelItem);
        if (orderChannelCollectionIdentifiers.includes(orderChannelIdentifier)) {
          return false;
        }
        orderChannelCollectionIdentifiers.push(orderChannelIdentifier);
        return true;
      });
      return [...orderChannelsToAdd, ...orderChannelCollection];
    }
    return orderChannelCollection;
  }
}
