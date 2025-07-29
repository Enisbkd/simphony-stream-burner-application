import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrderChannelBI } from '../order-channel-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../order-channel-bi.test-samples';

import { OrderChannelBIService } from './order-channel-bi.service';

const requireRestSample: IOrderChannelBI = {
  ...sampleWithRequiredData,
};

describe('OrderChannelBI Service', () => {
  let service: OrderChannelBIService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrderChannelBI | IOrderChannelBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrderChannelBIService);
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

    it('should create a OrderChannelBI', () => {
      const orderChannelBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(orderChannelBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderChannelBI', () => {
      const orderChannelBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(orderChannelBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderChannelBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderChannelBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrderChannelBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrderChannelBIToCollectionIfMissing', () => {
      it('should add a OrderChannelBI to an empty array', () => {
        const orderChannelBI: IOrderChannelBI = sampleWithRequiredData;
        expectedResult = service.addOrderChannelBIToCollectionIfMissing([], orderChannelBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderChannelBI);
      });

      it('should not add a OrderChannelBI to an array that contains it', () => {
        const orderChannelBI: IOrderChannelBI = sampleWithRequiredData;
        const orderChannelBICollection: IOrderChannelBI[] = [
          {
            ...orderChannelBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrderChannelBIToCollectionIfMissing(orderChannelBICollection, orderChannelBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderChannelBI to an array that doesn't contain it", () => {
        const orderChannelBI: IOrderChannelBI = sampleWithRequiredData;
        const orderChannelBICollection: IOrderChannelBI[] = [sampleWithPartialData];
        expectedResult = service.addOrderChannelBIToCollectionIfMissing(orderChannelBICollection, orderChannelBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderChannelBI);
      });

      it('should add only unique OrderChannelBI to an array', () => {
        const orderChannelBIArray: IOrderChannelBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const orderChannelBICollection: IOrderChannelBI[] = [sampleWithRequiredData];
        expectedResult = service.addOrderChannelBIToCollectionIfMissing(orderChannelBICollection, ...orderChannelBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderChannelBI: IOrderChannelBI = sampleWithRequiredData;
        const orderChannelBI2: IOrderChannelBI = sampleWithPartialData;
        expectedResult = service.addOrderChannelBIToCollectionIfMissing([], orderChannelBI, orderChannelBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderChannelBI);
        expect(expectedResult).toContain(orderChannelBI2);
      });

      it('should accept null and undefined values', () => {
        const orderChannelBI: IOrderChannelBI = sampleWithRequiredData;
        expectedResult = service.addOrderChannelBIToCollectionIfMissing([], null, orderChannelBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderChannelBI);
      });

      it('should return initial array if no OrderChannelBI is added', () => {
        const orderChannelBICollection: IOrderChannelBI[] = [sampleWithRequiredData];
        expectedResult = service.addOrderChannelBIToCollectionIfMissing(orderChannelBICollection, undefined, null);
        expect(expectedResult).toEqual(orderChannelBICollection);
      });
    });

    describe('compareOrderChannelBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrderChannelBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 32611 };
        const entity2 = null;

        const compareResult1 = service.compareOrderChannelBI(entity1, entity2);
        const compareResult2 = service.compareOrderChannelBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 32611 };
        const entity2 = { id: 3386 };

        const compareResult1 = service.compareOrderChannelBI(entity1, entity2);
        const compareResult2 = service.compareOrderChannelBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 32611 };
        const entity2 = { id: 32611 };

        const compareResult1 = service.compareOrderChannelBI(entity1, entity2);
        const compareResult2 = service.compareOrderChannelBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
