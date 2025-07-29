import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICommissionServiceBI } from '../commission-service-bi.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../commission-service-bi.test-samples';

import { CommissionServiceBIService } from './commission-service-bi.service';

const requireRestSample: ICommissionServiceBI = {
  ...sampleWithRequiredData,
};

describe('CommissionServiceBI Service', () => {
  let service: CommissionServiceBIService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommissionServiceBI | ICommissionServiceBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CommissionServiceBIService);
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

    it('should create a CommissionServiceBI', () => {
      const commissionServiceBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(commissionServiceBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommissionServiceBI', () => {
      const commissionServiceBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(commissionServiceBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommissionServiceBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommissionServiceBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CommissionServiceBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommissionServiceBIToCollectionIfMissing', () => {
      it('should add a CommissionServiceBI to an empty array', () => {
        const commissionServiceBI: ICommissionServiceBI = sampleWithRequiredData;
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing([], commissionServiceBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commissionServiceBI);
      });

      it('should not add a CommissionServiceBI to an array that contains it', () => {
        const commissionServiceBI: ICommissionServiceBI = sampleWithRequiredData;
        const commissionServiceBICollection: ICommissionServiceBI[] = [
          {
            ...commissionServiceBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing(commissionServiceBICollection, commissionServiceBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommissionServiceBI to an array that doesn't contain it", () => {
        const commissionServiceBI: ICommissionServiceBI = sampleWithRequiredData;
        const commissionServiceBICollection: ICommissionServiceBI[] = [sampleWithPartialData];
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing(commissionServiceBICollection, commissionServiceBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commissionServiceBI);
      });

      it('should add only unique CommissionServiceBI to an array', () => {
        const commissionServiceBIArray: ICommissionServiceBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commissionServiceBICollection: ICommissionServiceBI[] = [sampleWithRequiredData];
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing(commissionServiceBICollection, ...commissionServiceBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commissionServiceBI: ICommissionServiceBI = sampleWithRequiredData;
        const commissionServiceBI2: ICommissionServiceBI = sampleWithPartialData;
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing([], commissionServiceBI, commissionServiceBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commissionServiceBI);
        expect(expectedResult).toContain(commissionServiceBI2);
      });

      it('should accept null and undefined values', () => {
        const commissionServiceBI: ICommissionServiceBI = sampleWithRequiredData;
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing([], null, commissionServiceBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commissionServiceBI);
      });

      it('should return initial array if no CommissionServiceBI is added', () => {
        const commissionServiceBICollection: ICommissionServiceBI[] = [sampleWithRequiredData];
        expectedResult = service.addCommissionServiceBIToCollectionIfMissing(commissionServiceBICollection, undefined, null);
        expect(expectedResult).toEqual(commissionServiceBICollection);
      });
    });

    describe('compareCommissionServiceBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommissionServiceBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 7638 };
        const entity2 = null;

        const compareResult1 = service.compareCommissionServiceBI(entity1, entity2);
        const compareResult2 = service.compareCommissionServiceBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 7638 };
        const entity2 = { id: 32191 };

        const compareResult1 = service.compareCommissionServiceBI(entity1, entity2);
        const compareResult2 = service.compareCommissionServiceBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 7638 };
        const entity2 = { id: 7638 };

        const compareResult1 = service.compareCommissionServiceBI(entity1, entity2);
        const compareResult2 = service.compareCommissionServiceBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
