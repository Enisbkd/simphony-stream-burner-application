import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommissionServiceTrans, NewCommissionServiceTrans } from '../commission-service-trans.model';

export type PartialUpdateCommissionServiceTrans = Partial<ICommissionServiceTrans> & Pick<ICommissionServiceTrans, 'id'>;

export type EntityResponseType = HttpResponse<ICommissionServiceTrans>;
export type EntityArrayResponseType = HttpResponse<ICommissionServiceTrans[]>;

@Injectable({ providedIn: 'root' })
export class CommissionServiceTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commission-service-trans');

  create(commissionServiceTrans: NewCommissionServiceTrans): Observable<EntityResponseType> {
    return this.http.post<ICommissionServiceTrans>(this.resourceUrl, commissionServiceTrans, { observe: 'response' });
  }

  update(commissionServiceTrans: ICommissionServiceTrans): Observable<EntityResponseType> {
    return this.http.put<ICommissionServiceTrans>(
      `${this.resourceUrl}/${this.getCommissionServiceTransIdentifier(commissionServiceTrans)}`,
      commissionServiceTrans,
      { observe: 'response' },
    );
  }

  partialUpdate(commissionServiceTrans: PartialUpdateCommissionServiceTrans): Observable<EntityResponseType> {
    return this.http.patch<ICommissionServiceTrans>(
      `${this.resourceUrl}/${this.getCommissionServiceTransIdentifier(commissionServiceTrans)}`,
      commissionServiceTrans,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommissionServiceTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommissionServiceTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommissionServiceTransIdentifier(commissionServiceTrans: Pick<ICommissionServiceTrans, 'id'>): number {
    return commissionServiceTrans.id;
  }

  compareCommissionServiceTrans(o1: Pick<ICommissionServiceTrans, 'id'> | null, o2: Pick<ICommissionServiceTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommissionServiceTransIdentifier(o1) === this.getCommissionServiceTransIdentifier(o2) : o1 === o2;
  }

  addCommissionServiceTransToCollectionIfMissing<Type extends Pick<ICommissionServiceTrans, 'id'>>(
    commissionServiceTransCollection: Type[],
    ...commissionServiceTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const commissionServiceTrans: Type[] = commissionServiceTransToCheck.filter(isPresent);
    if (commissionServiceTrans.length > 0) {
      const commissionServiceTransCollectionIdentifiers = commissionServiceTransCollection.map(commissionServiceTransItem =>
        this.getCommissionServiceTransIdentifier(commissionServiceTransItem),
      );
      const commissionServiceTransToAdd = commissionServiceTrans.filter(commissionServiceTransItem => {
        const commissionServiceTransIdentifier = this.getCommissionServiceTransIdentifier(commissionServiceTransItem);
        if (commissionServiceTransCollectionIdentifiers.includes(commissionServiceTransIdentifier)) {
          return false;
        }
        commissionServiceTransCollectionIdentifiers.push(commissionServiceTransIdentifier);
        return true;
      });
      return [...commissionServiceTransToAdd, ...commissionServiceTransCollection];
    }
    return commissionServiceTransCollection;
  }
}
