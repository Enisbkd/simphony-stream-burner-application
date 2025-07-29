import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointDeVenteTrans, NewPointDeVenteTrans } from '../point-de-vente-trans.model';

export type PartialUpdatePointDeVenteTrans = Partial<IPointDeVenteTrans> & Pick<IPointDeVenteTrans, 'id'>;

export type EntityResponseType = HttpResponse<IPointDeVenteTrans>;
export type EntityArrayResponseType = HttpResponse<IPointDeVenteTrans[]>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteTransService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-de-vente-trans');

  create(pointDeVenteTrans: NewPointDeVenteTrans): Observable<EntityResponseType> {
    return this.http.post<IPointDeVenteTrans>(this.resourceUrl, pointDeVenteTrans, { observe: 'response' });
  }

  update(pointDeVenteTrans: IPointDeVenteTrans): Observable<EntityResponseType> {
    return this.http.put<IPointDeVenteTrans>(
      `${this.resourceUrl}/${this.getPointDeVenteTransIdentifier(pointDeVenteTrans)}`,
      pointDeVenteTrans,
      { observe: 'response' },
    );
  }

  partialUpdate(pointDeVenteTrans: PartialUpdatePointDeVenteTrans): Observable<EntityResponseType> {
    return this.http.patch<IPointDeVenteTrans>(
      `${this.resourceUrl}/${this.getPointDeVenteTransIdentifier(pointDeVenteTrans)}`,
      pointDeVenteTrans,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPointDeVenteTrans>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPointDeVenteTrans[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPointDeVenteTransIdentifier(pointDeVenteTrans: Pick<IPointDeVenteTrans, 'id'>): number {
    return pointDeVenteTrans.id;
  }

  comparePointDeVenteTrans(o1: Pick<IPointDeVenteTrans, 'id'> | null, o2: Pick<IPointDeVenteTrans, 'id'> | null): boolean {
    return o1 && o2 ? this.getPointDeVenteTransIdentifier(o1) === this.getPointDeVenteTransIdentifier(o2) : o1 === o2;
  }

  addPointDeVenteTransToCollectionIfMissing<Type extends Pick<IPointDeVenteTrans, 'id'>>(
    pointDeVenteTransCollection: Type[],
    ...pointDeVenteTransToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pointDeVenteTrans: Type[] = pointDeVenteTransToCheck.filter(isPresent);
    if (pointDeVenteTrans.length > 0) {
      const pointDeVenteTransCollectionIdentifiers = pointDeVenteTransCollection.map(pointDeVenteTransItem =>
        this.getPointDeVenteTransIdentifier(pointDeVenteTransItem),
      );
      const pointDeVenteTransToAdd = pointDeVenteTrans.filter(pointDeVenteTransItem => {
        const pointDeVenteTransIdentifier = this.getPointDeVenteTransIdentifier(pointDeVenteTransItem);
        if (pointDeVenteTransCollectionIdentifiers.includes(pointDeVenteTransIdentifier)) {
          return false;
        }
        pointDeVenteTransCollectionIdentifiers.push(pointDeVenteTransIdentifier);
        return true;
      });
      return [...pointDeVenteTransToAdd, ...pointDeVenteTransCollection];
    }
    return pointDeVenteTransCollection;
  }
}
