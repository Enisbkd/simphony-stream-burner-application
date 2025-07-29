import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommissionServiceBI, NewCommissionServiceBI } from '../commission-service-bi.model';

export type PartialUpdateCommissionServiceBI = Partial<ICommissionServiceBI> & Pick<ICommissionServiceBI, 'id'>;

export type EntityResponseType = HttpResponse<ICommissionServiceBI>;
export type EntityArrayResponseType = HttpResponse<ICommissionServiceBI[]>;

@Injectable({ providedIn: 'root' })
export class CommissionServiceBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commission-service-bis');

  create(commissionServiceBI: NewCommissionServiceBI): Observable<EntityResponseType> {
    return this.http.post<ICommissionServiceBI>(this.resourceUrl, commissionServiceBI, { observe: 'response' });
  }

  update(commissionServiceBI: ICommissionServiceBI): Observable<EntityResponseType> {
    return this.http.put<ICommissionServiceBI>(
      `${this.resourceUrl}/${this.getCommissionServiceBIIdentifier(commissionServiceBI)}`,
      commissionServiceBI,
      { observe: 'response' },
    );
  }

  partialUpdate(commissionServiceBI: PartialUpdateCommissionServiceBI): Observable<EntityResponseType> {
    return this.http.patch<ICommissionServiceBI>(
      `${this.resourceUrl}/${this.getCommissionServiceBIIdentifier(commissionServiceBI)}`,
      commissionServiceBI,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommissionServiceBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommissionServiceBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommissionServiceBIIdentifier(commissionServiceBI: Pick<ICommissionServiceBI, 'id'>): number {
    return commissionServiceBI.id;
  }

  compareCommissionServiceBI(o1: Pick<ICommissionServiceBI, 'id'> | null, o2: Pick<ICommissionServiceBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommissionServiceBIIdentifier(o1) === this.getCommissionServiceBIIdentifier(o2) : o1 === o2;
  }

  addCommissionServiceBIToCollectionIfMissing<Type extends Pick<ICommissionServiceBI, 'id'>>(
    commissionServiceBICollection: Type[],
    ...commissionServiceBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const commissionServiceBIS: Type[] = commissionServiceBISToCheck.filter(isPresent);
    if (commissionServiceBIS.length > 0) {
      const commissionServiceBICollectionIdentifiers = commissionServiceBICollection.map(commissionServiceBIItem =>
        this.getCommissionServiceBIIdentifier(commissionServiceBIItem),
      );
      const commissionServiceBISToAdd = commissionServiceBIS.filter(commissionServiceBIItem => {
        const commissionServiceBIIdentifier = this.getCommissionServiceBIIdentifier(commissionServiceBIItem);
        if (commissionServiceBICollectionIdentifiers.includes(commissionServiceBIIdentifier)) {
          return false;
        }
        commissionServiceBICollectionIdentifiers.push(commissionServiceBIIdentifier);
        return true;
      });
      return [...commissionServiceBISToAdd, ...commissionServiceBICollection];
    }
    return commissionServiceBICollection;
  }
}
