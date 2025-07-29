import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHttpCallAudit, NewHttpCallAudit } from '../http-call-audit.model';

export type PartialUpdateHttpCallAudit = Partial<IHttpCallAudit> & Pick<IHttpCallAudit, 'id'>;

type RestOf<T extends IHttpCallAudit | NewHttpCallAudit> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestHttpCallAudit = RestOf<IHttpCallAudit>;

export type NewRestHttpCallAudit = RestOf<NewHttpCallAudit>;

export type PartialUpdateRestHttpCallAudit = RestOf<PartialUpdateHttpCallAudit>;

export type EntityResponseType = HttpResponse<IHttpCallAudit>;
export type EntityArrayResponseType = HttpResponse<IHttpCallAudit[]>;

@Injectable({ providedIn: 'root' })
export class HttpCallAuditService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/http-call-audits');

  create(httpCallAudit: NewHttpCallAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(httpCallAudit);
    return this.http
      .post<RestHttpCallAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(httpCallAudit: IHttpCallAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(httpCallAudit);
    return this.http
      .put<RestHttpCallAudit>(`${this.resourceUrl}/${this.getHttpCallAuditIdentifier(httpCallAudit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(httpCallAudit: PartialUpdateHttpCallAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(httpCallAudit);
    return this.http
      .patch<RestHttpCallAudit>(`${this.resourceUrl}/${this.getHttpCallAuditIdentifier(httpCallAudit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestHttpCallAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestHttpCallAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHttpCallAuditIdentifier(httpCallAudit: Pick<IHttpCallAudit, 'id'>): number {
    return httpCallAudit.id;
  }

  compareHttpCallAudit(o1: Pick<IHttpCallAudit, 'id'> | null, o2: Pick<IHttpCallAudit, 'id'> | null): boolean {
    return o1 && o2 ? this.getHttpCallAuditIdentifier(o1) === this.getHttpCallAuditIdentifier(o2) : o1 === o2;
  }

  addHttpCallAuditToCollectionIfMissing<Type extends Pick<IHttpCallAudit, 'id'>>(
    httpCallAuditCollection: Type[],
    ...httpCallAuditsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const httpCallAudits: Type[] = httpCallAuditsToCheck.filter(isPresent);
    if (httpCallAudits.length > 0) {
      const httpCallAuditCollectionIdentifiers = httpCallAuditCollection.map(httpCallAuditItem =>
        this.getHttpCallAuditIdentifier(httpCallAuditItem),
      );
      const httpCallAuditsToAdd = httpCallAudits.filter(httpCallAuditItem => {
        const httpCallAuditIdentifier = this.getHttpCallAuditIdentifier(httpCallAuditItem);
        if (httpCallAuditCollectionIdentifiers.includes(httpCallAuditIdentifier)) {
          return false;
        }
        httpCallAuditCollectionIdentifiers.push(httpCallAuditIdentifier);
        return true;
      });
      return [...httpCallAuditsToAdd, ...httpCallAuditCollection];
    }
    return httpCallAuditCollection;
  }

  protected convertDateFromClient<T extends IHttpCallAudit | NewHttpCallAudit | PartialUpdateHttpCallAudit>(httpCallAudit: T): RestOf<T> {
    return {
      ...httpCallAudit,
      timestamp: httpCallAudit.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restHttpCallAudit: RestHttpCallAudit): IHttpCallAudit {
    return {
      ...restHttpCallAudit,
      timestamp: restHttpCallAudit.timestamp ? dayjs(restHttpCallAudit.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestHttpCallAudit>): HttpResponse<IHttpCallAudit> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestHttpCallAudit[]>): HttpResponse<IHttpCallAudit[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
