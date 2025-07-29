import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetailLineBI, NewDetailLineBI } from '../detail-line-bi.model';

export type PartialUpdateDetailLineBI = Partial<IDetailLineBI> & Pick<IDetailLineBI, 'id'>;

type RestOf<T extends IDetailLineBI | NewDetailLineBI> = Omit<T, 'detailUTC' | 'detailLcl'> & {
  detailUTC?: string | null;
  detailLcl?: string | null;
};

export type RestDetailLineBI = RestOf<IDetailLineBI>;

export type NewRestDetailLineBI = RestOf<NewDetailLineBI>;

export type PartialUpdateRestDetailLineBI = RestOf<PartialUpdateDetailLineBI>;

export type EntityResponseType = HttpResponse<IDetailLineBI>;
export type EntityArrayResponseType = HttpResponse<IDetailLineBI[]>;

@Injectable({ providedIn: 'root' })
export class DetailLineBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detail-line-bis');

  create(detailLineBI: NewDetailLineBI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailLineBI);
    return this.http
      .post<RestDetailLineBI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(detailLineBI: IDetailLineBI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailLineBI);
    return this.http
      .put<RestDetailLineBI>(`${this.resourceUrl}/${this.getDetailLineBIIdentifier(detailLineBI)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(detailLineBI: PartialUpdateDetailLineBI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detailLineBI);
    return this.http
      .patch<RestDetailLineBI>(`${this.resourceUrl}/${this.getDetailLineBIIdentifier(detailLineBI)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDetailLineBI>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDetailLineBI[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetailLineBIIdentifier(detailLineBI: Pick<IDetailLineBI, 'id'>): number {
    return detailLineBI.id;
  }

  compareDetailLineBI(o1: Pick<IDetailLineBI, 'id'> | null, o2: Pick<IDetailLineBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getDetailLineBIIdentifier(o1) === this.getDetailLineBIIdentifier(o2) : o1 === o2;
  }

  addDetailLineBIToCollectionIfMissing<Type extends Pick<IDetailLineBI, 'id'>>(
    detailLineBICollection: Type[],
    ...detailLineBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const detailLineBIS: Type[] = detailLineBISToCheck.filter(isPresent);
    if (detailLineBIS.length > 0) {
      const detailLineBICollectionIdentifiers = detailLineBICollection.map(detailLineBIItem =>
        this.getDetailLineBIIdentifier(detailLineBIItem),
      );
      const detailLineBISToAdd = detailLineBIS.filter(detailLineBIItem => {
        const detailLineBIIdentifier = this.getDetailLineBIIdentifier(detailLineBIItem);
        if (detailLineBICollectionIdentifiers.includes(detailLineBIIdentifier)) {
          return false;
        }
        detailLineBICollectionIdentifiers.push(detailLineBIIdentifier);
        return true;
      });
      return [...detailLineBISToAdd, ...detailLineBICollection];
    }
    return detailLineBICollection;
  }

  protected convertDateFromClient<T extends IDetailLineBI | NewDetailLineBI | PartialUpdateDetailLineBI>(detailLineBI: T): RestOf<T> {
    return {
      ...detailLineBI,
      detailUTC: detailLineBI.detailUTC?.toJSON() ?? null,
      detailLcl: detailLineBI.detailLcl?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDetailLineBI: RestDetailLineBI): IDetailLineBI {
    return {
      ...restDetailLineBI,
      detailUTC: restDetailLineBI.detailUTC ? dayjs(restDetailLineBI.detailUTC) : undefined,
      detailLcl: restDetailLineBI.detailLcl ? dayjs(restDetailLineBI.detailLcl) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDetailLineBI>): HttpResponse<IDetailLineBI> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDetailLineBI[]>): HttpResponse<IDetailLineBI[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
