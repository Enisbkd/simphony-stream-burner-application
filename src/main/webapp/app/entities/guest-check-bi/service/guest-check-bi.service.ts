import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuestCheckBI, NewGuestCheckBI } from '../guest-check-bi.model';

export type PartialUpdateGuestCheckBI = Partial<IGuestCheckBI> & Pick<IGuestCheckBI, 'id'>;

type RestOf<T extends IGuestCheckBI | NewGuestCheckBI> = Omit<T, 'opnLcl' | 'clsdLcl'> & {
  opnLcl?: string | null;
  clsdLcl?: string | null;
};

export type RestGuestCheckBI = RestOf<IGuestCheckBI>;

export type NewRestGuestCheckBI = RestOf<NewGuestCheckBI>;

export type PartialUpdateRestGuestCheckBI = RestOf<PartialUpdateGuestCheckBI>;

export type EntityResponseType = HttpResponse<IGuestCheckBI>;
export type EntityArrayResponseType = HttpResponse<IGuestCheckBI[]>;

@Injectable({ providedIn: 'root' })
export class GuestCheckBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guest-check-bis');

  create(guestCheckBI: NewGuestCheckBI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guestCheckBI);
    return this.http
      .post<RestGuestCheckBI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(guestCheckBI: IGuestCheckBI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guestCheckBI);
    return this.http
      .put<RestGuestCheckBI>(`${this.resourceUrl}/${this.getGuestCheckBIIdentifier(guestCheckBI)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(guestCheckBI: PartialUpdateGuestCheckBI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guestCheckBI);
    return this.http
      .patch<RestGuestCheckBI>(`${this.resourceUrl}/${this.getGuestCheckBIIdentifier(guestCheckBI)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGuestCheckBI>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGuestCheckBI[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuestCheckBIIdentifier(guestCheckBI: Pick<IGuestCheckBI, 'id'>): number {
    return guestCheckBI.id;
  }

  compareGuestCheckBI(o1: Pick<IGuestCheckBI, 'id'> | null, o2: Pick<IGuestCheckBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestCheckBIIdentifier(o1) === this.getGuestCheckBIIdentifier(o2) : o1 === o2;
  }

  addGuestCheckBIToCollectionIfMissing<Type extends Pick<IGuestCheckBI, 'id'>>(
    guestCheckBICollection: Type[],
    ...guestCheckBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guestCheckBIS: Type[] = guestCheckBISToCheck.filter(isPresent);
    if (guestCheckBIS.length > 0) {
      const guestCheckBICollectionIdentifiers = guestCheckBICollection.map(guestCheckBIItem =>
        this.getGuestCheckBIIdentifier(guestCheckBIItem),
      );
      const guestCheckBISToAdd = guestCheckBIS.filter(guestCheckBIItem => {
        const guestCheckBIIdentifier = this.getGuestCheckBIIdentifier(guestCheckBIItem);
        if (guestCheckBICollectionIdentifiers.includes(guestCheckBIIdentifier)) {
          return false;
        }
        guestCheckBICollectionIdentifiers.push(guestCheckBIIdentifier);
        return true;
      });
      return [...guestCheckBISToAdd, ...guestCheckBICollection];
    }
    return guestCheckBICollection;
  }

  protected convertDateFromClient<T extends IGuestCheckBI | NewGuestCheckBI | PartialUpdateGuestCheckBI>(guestCheckBI: T): RestOf<T> {
    return {
      ...guestCheckBI,
      opnLcl: guestCheckBI.opnLcl?.toJSON() ?? null,
      clsdLcl: guestCheckBI.clsdLcl?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGuestCheckBI: RestGuestCheckBI): IGuestCheckBI {
    return {
      ...restGuestCheckBI,
      opnLcl: restGuestCheckBI.opnLcl ? dayjs(restGuestCheckBI.opnLcl) : undefined,
      clsdLcl: restGuestCheckBI.clsdLcl ? dayjs(restGuestCheckBI.clsdLcl) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGuestCheckBI>): HttpResponse<IGuestCheckBI> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGuestCheckBI[]>): HttpResponse<IGuestCheckBI[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
