import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICommissionServiceTrans } from '../commission-service-trans.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../commission-service-trans.test-samples';

import { CommissionServiceTransService } from './commission-service-trans.service';

const requireRestSample: ICommissionServiceTrans = {
  ...sampleWithRequiredData,
};

describe('CommissionServiceTrans Service', () => {
  let service: CommissionServiceTransService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommissionServiceTrans | ICommissionServiceTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CommissionServiceTransService);
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

    it('should create a CommissionServiceTrans', () => {
      const commissionServiceTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(commissionServiceTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommissionServiceTrans', () => {
      const commissionServiceTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(commissionServiceTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommissionServiceTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommissionServiceTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CommissionServiceTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommissionServiceTransToCollectionIfMissing', () => {
      it('should add a CommissionServiceTrans to an empty array', () => {
        const commissionServiceTrans: ICommissionServiceTrans = sampleWithRequiredData;
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing([], commissionServiceTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commissionServiceTrans);
      });

      it('should not add a CommissionServiceTrans to an array that contains it', () => {
        const commissionServiceTrans: ICommissionServiceTrans = sampleWithRequiredData;
        const commissionServiceTransCollection: ICommissionServiceTrans[] = [
          {
            ...commissionServiceTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing(commissionServiceTransCollection, commissionServiceTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommissionServiceTrans to an array that doesn't contain it", () => {
        const commissionServiceTrans: ICommissionServiceTrans = sampleWithRequiredData;
        const commissionServiceTransCollection: ICommissionServiceTrans[] = [sampleWithPartialData];
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing(commissionServiceTransCollection, commissionServiceTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commissionServiceTrans);
      });

      it('should add only unique CommissionServiceTrans to an array', () => {
        const commissionServiceTransArray: ICommissionServiceTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commissionServiceTransCollection: ICommissionServiceTrans[] = [sampleWithRequiredData];
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing(
          commissionServiceTransCollection,
          ...commissionServiceTransArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commissionServiceTrans: ICommissionServiceTrans = sampleWithRequiredData;
        const commissionServiceTrans2: ICommissionServiceTrans = sampleWithPartialData;
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing([], commissionServiceTrans, commissionServiceTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commissionServiceTrans);
        expect(expectedResult).toContain(commissionServiceTrans2);
      });

      it('should accept null and undefined values', () => {
        const commissionServiceTrans: ICommissionServiceTrans = sampleWithRequiredData;
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing([], null, commissionServiceTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commissionServiceTrans);
      });

      it('should return initial array if no CommissionServiceTrans is added', () => {
        const commissionServiceTransCollection: ICommissionServiceTrans[] = [sampleWithRequiredData];
        expectedResult = service.addCommissionServiceTransToCollectionIfMissing(commissionServiceTransCollection, undefined, null);
        expect(expectedResult).toEqual(commissionServiceTransCollection);
      });
    });

    describe('compareCommissionServiceTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommissionServiceTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 29235 };
        const entity2 = null;

        const compareResult1 = service.compareCommissionServiceTrans(entity1, entity2);
        const compareResult2 = service.compareCommissionServiceTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 29235 };
        const entity2 = { id: 12705 };

        const compareResult1 = service.compareCommissionServiceTrans(entity1, entity2);
        const compareResult2 = service.compareCommissionServiceTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 29235 };
        const entity2 = { id: 29235 };

        const compareResult1 = service.compareCommissionServiceTrans(entity1, entity2);
        const compareResult2 = service.compareCommissionServiceTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
