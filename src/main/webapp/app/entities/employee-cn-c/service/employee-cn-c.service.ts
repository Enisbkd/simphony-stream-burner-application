import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployeeCnC, NewEmployeeCnC } from '../employee-cn-c.model';

export type PartialUpdateEmployeeCnC = Partial<IEmployeeCnC> & Pick<IEmployeeCnC, 'id'>;

export type EntityResponseType = HttpResponse<IEmployeeCnC>;
export type EntityArrayResponseType = HttpResponse<IEmployeeCnC[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeCnCService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employee-cn-cs');

  create(employeeCnC: NewEmployeeCnC): Observable<EntityResponseType> {
    return this.http.post<IEmployeeCnC>(this.resourceUrl, employeeCnC, { observe: 'response' });
  }

  update(employeeCnC: IEmployeeCnC): Observable<EntityResponseType> {
    return this.http.put<IEmployeeCnC>(`${this.resourceUrl}/${this.getEmployeeCnCIdentifier(employeeCnC)}`, employeeCnC, {
      observe: 'response',
    });
  }

  partialUpdate(employeeCnC: PartialUpdateEmployeeCnC): Observable<EntityResponseType> {
    return this.http.patch<IEmployeeCnC>(`${this.resourceUrl}/${this.getEmployeeCnCIdentifier(employeeCnC)}`, employeeCnC, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeCnC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeCnC[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmployeeCnCIdentifier(employeeCnC: Pick<IEmployeeCnC, 'id'>): number {
    return employeeCnC.id;
  }

  compareEmployeeCnC(o1: Pick<IEmployeeCnC, 'id'> | null, o2: Pick<IEmployeeCnC, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmployeeCnCIdentifier(o1) === this.getEmployeeCnCIdentifier(o2) : o1 === o2;
  }

  addEmployeeCnCToCollectionIfMissing<Type extends Pick<IEmployeeCnC, 'id'>>(
    employeeCnCCollection: Type[],
    ...employeeCnCSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const employeeCnCS: Type[] = employeeCnCSToCheck.filter(isPresent);
    if (employeeCnCS.length > 0) {
      const employeeCnCCollectionIdentifiers = employeeCnCCollection.map(employeeCnCItem => this.getEmployeeCnCIdentifier(employeeCnCItem));
      const employeeCnCSToAdd = employeeCnCS.filter(employeeCnCItem => {
        const employeeCnCIdentifier = this.getEmployeeCnCIdentifier(employeeCnCItem);
        if (employeeCnCCollectionIdentifiers.includes(employeeCnCIdentifier)) {
          return false;
        }
        employeeCnCCollectionIdentifiers.push(employeeCnCIdentifier);
        return true;
      });
      return [...employeeCnCSToAdd, ...employeeCnCCollection];
    }
    return employeeCnCCollection;
  }
}
