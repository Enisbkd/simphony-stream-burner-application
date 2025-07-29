import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IHttpCallAudit } from '../http-call-audit.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../http-call-audit.test-samples';

import { HttpCallAuditService, RestHttpCallAudit } from './http-call-audit.service';

const requireRestSample: RestHttpCallAudit = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('HttpCallAudit Service', () => {
  let service: HttpCallAuditService;
  let httpMock: HttpTestingController;
  let expectedResult: IHttpCallAudit | IHttpCallAudit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(HttpCallAuditService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a HttpCallAudit', () => {
      const httpCallAudit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(httpCallAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HttpCallAudit', () => {
      const httpCallAudit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(httpCallAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HttpCallAudit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HttpCallAudit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HttpCallAudit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHttpCallAuditToCollectionIfMissing', () => {
      it('should add a HttpCallAudit to an empty array', () => {
        const httpCallAudit: IHttpCallAudit = sampleWithRequiredData;
        expectedResult = service.addHttpCallAuditToCollectionIfMissing([], httpCallAudit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(httpCallAudit);
      });

      it('should not add a HttpCallAudit to an array that contains it', () => {
        const httpCallAudit: IHttpCallAudit = sampleWithRequiredData;
        const httpCallAuditCollection: IHttpCallAudit[] = [
          {
            ...httpCallAudit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHttpCallAuditToCollectionIfMissing(httpCallAuditCollection, httpCallAudit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HttpCallAudit to an array that doesn't contain it", () => {
        const httpCallAudit: IHttpCallAudit = sampleWithRequiredData;
        const httpCallAuditCollection: IHttpCallAudit[] = [sampleWithPartialData];
        expectedResult = service.addHttpCallAuditToCollectionIfMissing(httpCallAuditCollection, httpCallAudit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(httpCallAudit);
      });

      it('should add only unique HttpCallAudit to an array', () => {
        const httpCallAuditArray: IHttpCallAudit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const httpCallAuditCollection: IHttpCallAudit[] = [sampleWithRequiredData];
        expectedResult = service.addHttpCallAuditToCollectionIfMissing(httpCallAuditCollection, ...httpCallAuditArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const httpCallAudit: IHttpCallAudit = sampleWithRequiredData;
        const httpCallAudit2: IHttpCallAudit = sampleWithPartialData;
        expectedResult = service.addHttpCallAuditToCollectionIfMissing([], httpCallAudit, httpCallAudit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(httpCallAudit);
        expect(expectedResult).toContain(httpCallAudit2);
      });

      it('should accept null and undefined values', () => {
        const httpCallAudit: IHttpCallAudit = sampleWithRequiredData;
        expectedResult = service.addHttpCallAuditToCollectionIfMissing([], null, httpCallAudit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(httpCallAudit);
      });

      it('should return initial array if no HttpCallAudit is added', () => {
        const httpCallAuditCollection: IHttpCallAudit[] = [sampleWithRequiredData];
        expectedResult = service.addHttpCallAuditToCollectionIfMissing(httpCallAuditCollection, undefined, null);
        expect(expectedResult).toEqual(httpCallAuditCollection);
      });
    });

    describe('compareHttpCallAudit', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHttpCallAudit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18456 };
        const entity2 = null;

        const compareResult1 = service.compareHttpCallAudit(entity1, entity2);
        const compareResult2 = service.compareHttpCallAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18456 };
        const entity2 = { id: 15047 };

        const compareResult1 = service.compareHttpCallAudit(entity1, entity2);
        const compareResult2 = service.compareHttpCallAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18456 };
        const entity2 = { id: 18456 };

        const compareResult1 = service.compareHttpCallAudit(entity1, entity2);
        const compareResult2 = service.compareHttpCallAudit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
