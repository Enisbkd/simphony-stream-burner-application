import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrderTypeBI } from '../order-type-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../order-type-bi.test-samples';

import { OrderTypeBIService } from './order-type-bi.service';

const requireRestSample: IOrderTypeBI = {
  ...sampleWithRequiredData,
};

describe('OrderTypeBI Service', () => {
  let service: OrderTypeBIService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrderTypeBI | IOrderTypeBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrderTypeBIService);
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

    it('should create a OrderTypeBI', () => {
      const orderTypeBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(orderTypeBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderTypeBI', () => {
      const orderTypeBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(orderTypeBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderTypeBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderTypeBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrderTypeBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrderTypeBIToCollectionIfMissing', () => {
      it('should add a OrderTypeBI to an empty array', () => {
        const orderTypeBI: IOrderTypeBI = sampleWithRequiredData;
        expectedResult = service.addOrderTypeBIToCollectionIfMissing([], orderTypeBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderTypeBI);
      });

      it('should not add a OrderTypeBI to an array that contains it', () => {
        const orderTypeBI: IOrderTypeBI = sampleWithRequiredData;
        const orderTypeBICollection: IOrderTypeBI[] = [
          {
            ...orderTypeBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrderTypeBIToCollectionIfMissing(orderTypeBICollection, orderTypeBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderTypeBI to an array that doesn't contain it", () => {
        const orderTypeBI: IOrderTypeBI = sampleWithRequiredData;
        const orderTypeBICollection: IOrderTypeBI[] = [sampleWithPartialData];
        expectedResult = service.addOrderTypeBIToCollectionIfMissing(orderTypeBICollection, orderTypeBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderTypeBI);
      });

      it('should add only unique OrderTypeBI to an array', () => {
        const orderTypeBIArray: IOrderTypeBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const orderTypeBICollection: IOrderTypeBI[] = [sampleWithRequiredData];
        expectedResult = service.addOrderTypeBIToCollectionIfMissing(orderTypeBICollection, ...orderTypeBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderTypeBI: IOrderTypeBI = sampleWithRequiredData;
        const orderTypeBI2: IOrderTypeBI = sampleWithPartialData;
        expectedResult = service.addOrderTypeBIToCollectionIfMissing([], orderTypeBI, orderTypeBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderTypeBI);
        expect(expectedResult).toContain(orderTypeBI2);
      });

      it('should accept null and undefined values', () => {
        const orderTypeBI: IOrderTypeBI = sampleWithRequiredData;
        expectedResult = service.addOrderTypeBIToCollectionIfMissing([], null, orderTypeBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderTypeBI);
      });

      it('should return initial array if no OrderTypeBI is added', () => {
        const orderTypeBICollection: IOrderTypeBI[] = [sampleWithRequiredData];
        expectedResult = service.addOrderTypeBIToCollectionIfMissing(orderTypeBICollection, undefined, null);
        expect(expectedResult).toEqual(orderTypeBICollection);
      });
    });

    describe('compareOrderTypeBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrderTypeBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 10594 };
        const entity2 = null;

        const compareResult1 = service.compareOrderTypeBI(entity1, entity2);
        const compareResult2 = service.compareOrderTypeBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 10594 };
        const entity2 = { id: 22507 };

        const compareResult1 = service.compareOrderTypeBI(entity1, entity2);
        const compareResult2 = service.compareOrderTypeBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 10594 };
        const entity2 = { id: 10594 };

        const compareResult1 = service.compareOrderTypeBI(entity1, entity2);
        const compareResult2 = service.compareOrderTypeBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
