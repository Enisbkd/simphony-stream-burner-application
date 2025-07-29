import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckTrans, NewCheckTrans } from '../check-trans.model';

export type PartialUpdateCheckTrans = Partial<ICheckTrans> & Pick<ICheckTrans, 'id'>;

type RestOf<T extends ICheckTrans | NewCheckTrans> = Omit<T, 'openTime'> & {
  openTime?: string | null;
};

export type RestCheckTrans = RestOf<ICheckTrans>;

export type NewRestCheckTrans = RestOf<NewCheckTrans>;

export type PartialUpdateRestCheckTrans = RestOf<PartialUpdateCheckTrans>;

export type EntityResponseType = HttpResponse<ICheckTrans>;
export type EntityArrayResponseType = HttpResponse<ICheckTrans[]>;

@Injectable({ providedIn: 'root' })
export class CheckTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/check-trans');

  create(checkTrans: NewCheckTrans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkTrans);
    return this.http
      .post<RestCheckTrans>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(checkTrans: ICheckTrans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkTrans);
    return this.http
      .put<RestCheckTrans>(`${this.resourceUrl}/${this.getCheckTransIdentifier(checkTrans)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(checkTrans: PartialUpdateCheckTrans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkTrans);
    return this.http
      .patch<RestCheckTrans>(`${this.resourceUrl}/${this.getCheckTransIdentifier(checkTrans)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCheckTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCheckTrans[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCheckTransIdentifier(checkTrans: Pick<ICheckTrans, 'id'>): number {
    return checkTrans.id;
  }

  compareCheckTrans(o1: Pick<ICheckTrans, 'id'> | null, o2: Pick<ICheckTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getCheckTransIdentifier(o1) === this.getCheckTransIdentifier(o2) : o1 === o2;
  }

  addCheckTransToCollectionIfMissing<Type extends Pick<ICheckTrans, 'id'>>(
    checkTransCollection: Type[],
    ...checkTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const checkTrans: Type[] = checkTransToCheck.filter(isPresent);
    if (checkTrans.length > 0) {
      const checkTransCollectionIdentifiers = checkTransCollection.map(checkTransItem => this.getCheckTransIdentifier(checkTransItem));
      const checkTransToAdd = checkTrans.filter(checkTransItem => {
        const checkTransIdentifier = this.getCheckTransIdentifier(checkTransItem);
        if (checkTransCollectionIdentifiers.includes(checkTransIdentifier)) {
          return false;
        }
        checkTransCollectionIdentifiers.push(checkTransIdentifier);
        return true;
      });
      return [...checkTransToAdd, ...checkTransCollection];
    }
    return checkTransCollection;
  }

  protected convertDateFromClient<T extends ICheckTrans | NewCheckTrans | PartialUpdateCheckTrans>(checkTrans: T): RestOf<T> {
    return {
      ...checkTrans,
      openTime: checkTrans.openTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCheckTrans: RestCheckTrans): ICheckTrans {
    return {
      ...restCheckTrans,
      openTime: restCheckTrans.openTime ? dayjs(restCheckTrans.openTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCheckTrans>): HttpResponse<ICheckTrans> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCheckTrans[]>): HttpResponse<ICheckTrans[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
