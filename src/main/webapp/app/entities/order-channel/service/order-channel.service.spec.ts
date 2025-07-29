import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrderChannel } from '../order-channel.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../order-channel.test-samples';

import { OrderChannelService } from './order-channel.service';

const requireRestSample: IOrderChannel = {
  ...sampleWithRequiredData,
};

describe('OrderChannel Service', () => {
  let service: OrderChannelService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrderChannel | IOrderChannel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrderChannelService);
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

    it('should create a OrderChannel', () => {
      const orderChannel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(orderChannel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderChannel', () => {
      const orderChannel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(orderChannel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderChannel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderChannel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrderChannel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrderChannelToCollectionIfMissing', () => {
      it('should add a OrderChannel to an empty array', () => {
        const orderChannel: IOrderChannel = sampleWithRequiredData;
        expectedResult = service.addOrderChannelToCollectionIfMissing([], orderChannel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderChannel);
      });

      it('should not add a OrderChannel to an array that contains it', () => {
        const orderChannel: IOrderChannel = sampleWithRequiredData;
        const orderChannelCollection: IOrderChannel[] = [
          {
            ...orderChannel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrderChannelToCollectionIfMissing(orderChannelCollection, orderChannel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderChannel to an array that doesn't contain it", () => {
        const orderChannel: IOrderChannel = sampleWithRequiredData;
        const orderChannelCollection: IOrderChannel[] = [sampleWithPartialData];
        expectedResult = service.addOrderChannelToCollectionIfMissing(orderChannelCollection, orderChannel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderChannel);
      });

      it('should add only unique OrderChannel to an array', () => {
        const orderChannelArray: IOrderChannel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const orderChannelCollection: IOrderChannel[] = [sampleWithRequiredData];
        expectedResult = service.addOrderChannelToCollectionIfMissing(orderChannelCollection, ...orderChannelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderChannel: IOrderChannel = sampleWithRequiredData;
        const orderChannel2: IOrderChannel = sampleWithPartialData;
        expectedResult = service.addOrderChannelToCollectionIfMissing([], orderChannel, orderChannel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderChannel);
        expect(expectedResult).toContain(orderChannel2);
      });

      it('should accept null and undefined values', () => {
        const orderChannel: IOrderChannel = sampleWithRequiredData;
        expectedResult = service.addOrderChannelToCollectionIfMissing([], null, orderChannel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderChannel);
      });

      it('should return initial array if no OrderChannel is added', () => {
        const orderChannelCollection: IOrderChannel[] = [sampleWithRequiredData];
        expectedResult = service.addOrderChannelToCollectionIfMissing(orderChannelCollection, undefined, null);
        expect(expectedResult).toEqual(orderChannelCollection);
      });
    });

    describe('compareOrderChannel', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrderChannel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 27066 };
        const entity2 = null;

        const compareResult1 = service.compareOrderChannel(entity1, entity2);
        const compareResult2 = service.compareOrderChannel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 27066 };
        const entity2 = { id: 20329 };

        const compareResult1 = service.compareOrderChannel(entity1, entity2);
        const compareResult2 = service.compareOrderChannel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 27066 };
        const entity2 = { id: 27066 };

        const compareResult1 = service.compareOrderChannel(entity1, entity2);
        const compareResult2 = service.compareOrderChannel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
