import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGuestCheckBI } from '../guest-check-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../guest-check-bi.test-samples';

import { GuestCheckBIService, RestGuestCheckBI } from './guest-check-bi.service';

const requireRestSample: RestGuestCheckBI = {
  ...sampleWithRequiredData,
  opnLcl: sampleWithRequiredData.opnLcl?.toJSON(),
  clsdLcl: sampleWithRequiredData.clsdLcl?.toJSON(),
};

describe('GuestCheckBI Service', () => {
  let service: GuestCheckBIService;
  let httpMock: HttpTestingController;
  let expectedResult: IGuestCheckBI | IGuestCheckBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GuestCheckBIService);
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

    it('should create a GuestCheckBI', () => {
      const guestCheckBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(guestCheckBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GuestCheckBI', () => {
      const guestCheckBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(guestCheckBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GuestCheckBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GuestCheckBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GuestCheckBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGuestCheckBIToCollectionIfMissing', () => {
      it('should add a GuestCheckBI to an empty array', () => {
        const guestCheckBI: IGuestCheckBI = sampleWithRequiredData;
        expectedResult = service.addGuestCheckBIToCollectionIfMissing([], guestCheckBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestCheckBI);
      });

      it('should not add a GuestCheckBI to an array that contains it', () => {
        const guestCheckBI: IGuestCheckBI = sampleWithRequiredData;
        const guestCheckBICollection: IGuestCheckBI[] = [
          {
            ...guestCheckBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGuestCheckBIToCollectionIfMissing(guestCheckBICollection, guestCheckBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GuestCheckBI to an array that doesn't contain it", () => {
        const guestCheckBI: IGuestCheckBI = sampleWithRequiredData;
        const guestCheckBICollection: IGuestCheckBI[] = [sampleWithPartialData];
        expectedResult = service.addGuestCheckBIToCollectionIfMissing(guestCheckBICollection, guestCheckBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestCheckBI);
      });

      it('should add only unique GuestCheckBI to an array', () => {
        const guestCheckBIArray: IGuestCheckBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const guestCheckBICollection: IGuestCheckBI[] = [sampleWithRequiredData];
        expectedResult = service.addGuestCheckBIToCollectionIfMissing(guestCheckBICollection, ...guestCheckBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const guestCheckBI: IGuestCheckBI = sampleWithRequiredData;
        const guestCheckBI2: IGuestCheckBI = sampleWithPartialData;
        expectedResult = service.addGuestCheckBIToCollectionIfMissing([], guestCheckBI, guestCheckBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestCheckBI);
        expect(expectedResult).toContain(guestCheckBI2);
      });

      it('should accept null and undefined values', () => {
        const guestCheckBI: IGuestCheckBI = sampleWithRequiredData;
        expectedResult = service.addGuestCheckBIToCollectionIfMissing([], null, guestCheckBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestCheckBI);
      });

      it('should return initial array if no GuestCheckBI is added', () => {
        const guestCheckBICollection: IGuestCheckBI[] = [sampleWithRequiredData];
        expectedResult = service.addGuestCheckBIToCollectionIfMissing(guestCheckBICollection, undefined, null);
        expect(expectedResult).toEqual(guestCheckBICollection);
      });
    });

    describe('compareGuestCheckBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGuestCheckBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 10485 };
        const entity2 = null;

        const compareResult1 = service.compareGuestCheckBI(entity1, entity2);
        const compareResult2 = service.compareGuestCheckBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 10485 };
        const entity2 = { id: 12162 };

        const compareResult1 = service.compareGuestCheckBI(entity1, entity2);
        const compareResult2 = service.compareGuestCheckBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 10485 };
        const entity2 = { id: 10485 };

        const compareResult1 = service.compareGuestCheckBI(entity1, entity2);
        const compareResult2 = service.compareGuestCheckBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
