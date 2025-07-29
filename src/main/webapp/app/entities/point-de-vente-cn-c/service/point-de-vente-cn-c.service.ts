import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointDeVenteCnC, NewPointDeVenteCnC } from '../point-de-vente-cn-c.model';

export type PartialUpdatePointDeVenteCnC = Partial<IPointDeVenteCnC> & Pick<IPointDeVenteCnC, 'id'>;

export type EntityResponseType = HttpResponse<IPointDeVenteCnC>;
export type EntityArrayResponseType = HttpResponse<IPointDeVenteCnC[]>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-de-vente-cn-cs');

  create(pointDeVenteCnC: NewPointDeVenteCnC): Observable<EntityResponseType> {
    return this.http.post<IPointDeVenteCnC>(this.resourceUrl, pointDeVenteCnC, { observe: 'response' });
  }

  update(pointDeVenteCnC: IPointDeVenteCnC): Observable<EntityResponseType> {
    return this.http.put<IPointDeVenteCnC>(`${this.resourceUrl}/${this.getPointDeVenteCnCIdentifier(pointDeVenteCnC)}`, pointDeVenteCnC, {
      observe: 'response',
    });
  }

  partialUpdate(pointDeVenteCnC: PartialUpdatePointDeVenteCnC): Observable<EntityResponseType> {
    return this.http.patch<IPointDeVenteCnC>(`${this.resourceUrl}/${this.getPointDeVenteCnCIdentifier(pointDeVenteCnC)}`, pointDeVenteCnC, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPointDeVenteCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPointDeVenteCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPointDeVenteCnCIdentifier(pointDeVenteCnC: Pick<IPointDeVenteCnC, 'id'>): number {
    return pointDeVenteCnC.id;
  }

  comparePointDeVenteCnC(o1: Pick<IPointDeVenteCnC, 'id'> | null, o2: Pick<IPointDeVenteCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getPointDeVenteCnCIdentifier(o1) === this.getPointDeVenteCnCIdentifier(o2) : o1 === o2;
  }

  addPointDeVenteCnCToCollectionIfMissing<Type extends Pick<IPointDeVenteCnC, 'id'>>(
    pointDeVenteCnCCollection: Type[],
    ...pointDeVenteCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pointDeVenteCnCS: Type[] = pointDeVenteCnCSToCheck.filter(isPresent);
    if (pointDeVenteCnCS.length > 0) {
      const pointDeVenteCnCCollectionIdentifiers = pointDeVenteCnCCollection.map(pointDeVenteCnCItem =>
        this.getPointDeVenteCnCIdentifier(pointDeVenteCnCItem),
      );
      const pointDeVenteCnCSToAdd = pointDeVenteCnCS.filter(pointDeVenteCnCItem => {
        const pointDeVenteCnCIdentifier = this.getPointDeVenteCnCIdentifier(pointDeVenteCnCItem);
        if (pointDeVenteCnCCollectionIdentifiers.includes(pointDeVenteCnCIdentifier)) {
          return false;
        }
        pointDeVenteCnCCollectionIdentifiers.push(pointDeVenteCnCIdentifier);
        return true;
      });
      return [...pointDeVenteCnCSToAdd, ...pointDeVenteCnCCollection];
    }
    return pointDeVenteCnCCollection;
  }
}
