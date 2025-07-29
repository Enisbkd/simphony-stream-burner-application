import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICodeRaisonBI, NewCodeRaisonBI } from '../code-raison-bi.model';

export type PartialUpdateCodeRaisonBI = Partial<ICodeRaisonBI> & Pick<ICodeRaisonBI, 'id'>;

export type EntityResponseType = HttpResponse<ICodeRaisonBI>;
export type EntityArrayResponseType = HttpResponse<ICodeRaisonBI[]>;

@Injectable({ providedIn: 'root' })
export class CodeRaisonBIService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/code-raison-bis');

  create(codeRaisonBI: NewCodeRaisonBI): Observable<EntityResponseType> {
    return this.http.post<ICodeRaisonBI>(this.resourceUrl, codeRaisonBI, { observe: 'response' });
  }

  update(codeRaisonBI: ICodeRaisonBI): Observable<EntityResponseType> {
    return this.http.put<ICodeRaisonBI>(`${this.resourceUrl}/${this.getCodeRaisonBIIdentifier(codeRaisonBI)}`, codeRaisonBI, {
      observe: 'response',
    });
  }

  partialUpdate(codeRaisonBI: PartialUpdateCodeRaisonBI): Observable<EntityResponseType> {
    return this.http.patch<ICodeRaisonBI>(`${this.resourceUrl}/${this.getCodeRaisonBIIdentifier(codeRaisonBI)}`, codeRaisonBI, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICodeRaisonBI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICodeRaisonBI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCodeRaisonBIIdentifier(codeRaisonBI: Pick<ICodeRaisonBI, 'id'>): number {
    return codeRaisonBI.id;
  }

  compareCodeRaisonBI(o1: Pick<ICodeRaisonBI, 'id'> | null, o2: Pick<ICodeRaisonBI, 'id'> | null): boolean {
    return o1 && o2 ? this.getCodeRaisonBIIdentifier(o1) === this.getCodeRaisonBIIdentifier(o2) : o1 === o2;
  }

  addCodeRaisonBIToCollectionIfMissing<Type extends Pick<ICodeRaisonBI, 'id'>>(
    codeRaisonBICollection: Type[],
    ...codeRaisonBISToCheck: (Type | null | undefined)[]
  ): Type[] {
    const codeRaisonBIS: Type[] = codeRaisonBISToCheck.filter(isPresent);
    if (codeRaisonBIS.length > 0) {
      const codeRaisonBICollectionIdentifiers = codeRaisonBICollection.map(codeRaisonBIItem =>
        this.getCodeRaisonBIIdentifier(codeRaisonBIItem),
      );
      const codeRaisonBISToAdd = codeRaisonBIS.filter(codeRaisonBIItem => {
        const codeRaisonBIIdentifier = this.getCodeRaisonBIIdentifier(codeRaisonBIItem);
        if (codeRaisonBICollectionIdentifiers.includes(codeRaisonBIIdentifier)) {
          return false;
        }
        codeRaisonBICollectionIdentifiers.push(codeRaisonBIIdentifier);
        return true;
      });
      return [...codeRaisonBISToAdd, ...codeRaisonBICollection];
    }
    return codeRaisonBICollection;
  }
}
