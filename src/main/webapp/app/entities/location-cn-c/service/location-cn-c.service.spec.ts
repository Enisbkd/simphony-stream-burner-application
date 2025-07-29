import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ILocationCnC } from '../location-cn-c.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../location-cn-c.test-samples';

import { LocationCnCService } from './location-cn-c.service';

const requireRestSample: ILocationCnC = {
  ...sampleWithRequiredData,
};

describe('LocationCnC Service', () => {
  let service: LocationCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: ILocationCnC | ILocationCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(LocationCnCService);
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

    it('should create a LocationCnC', () => {
      const locationCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(locationCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LocationCnC', () => {
      const locationCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(locationCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LocationCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LocationCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LocationCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLocationCnCToCollectionIfMissing', () => {
      it('should add a LocationCnC to an empty array', () => {
        const locationCnC: ILocationCnC = sampleWithRequiredData;
        expectedResult = service.addLocationCnCToCollectionIfMissing([], locationCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(locationCnC);
      });

      it('should not add a LocationCnC to an array that contains it', () => {
        const locationCnC: ILocationCnC = sampleWithRequiredData;
        const locationCnCCollection: ILocationCnC[] = [
          {
            ...locationCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLocationCnCToCollectionIfMissing(locationCnCCollection, locationCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LocationCnC to an array that doesn't contain it", () => {
        const locationCnC: ILocationCnC = sampleWithRequiredData;
        const locationCnCCollection: ILocationCnC[] = [sampleWithPartialData];
        expectedResult = service.addLocationCnCToCollectionIfMissing(locationCnCCollection, locationCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(locationCnC);
      });

      it('should add only unique LocationCnC to an array', () => {
        const locationCnCArray: ILocationCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const locationCnCCollection: ILocationCnC[] = [sampleWithRequiredData];
        expectedResult = service.addLocationCnCToCollectionIfMissing(locationCnCCollection, ...locationCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const locationCnC: ILocationCnC = sampleWithRequiredData;
        const locationCnC2: ILocationCnC = sampleWithPartialData;
        expectedResult = service.addLocationCnCToCollectionIfMissing([], locationCnC, locationCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(locationCnC);
        expect(expectedResult).toContain(locationCnC2);
      });

      it('should accept null and undefined values', () => {
        const locationCnC: ILocationCnC = sampleWithRequiredData;
        expectedResult = service.addLocationCnCToCollectionIfMissing([], null, locationCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(locationCnC);
      });

      it('should return initial array if no LocationCnC is added', () => {
        const locationCnCCollection: ILocationCnC[] = [sampleWithRequiredData];
        expectedResult = service.addLocationCnCToCollectionIfMissing(locationCnCCollection, undefined, null);
        expect(expectedResult).toEqual(locationCnCCollection);
      });
    });

    describe('compareLocationCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLocationCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 3212 };
        const entity2 = null;

        const compareResult1 = service.compareLocationCnC(entity1, entity2);
        const compareResult2 = service.compareLocationCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 3212 };
        const entity2 = { id: 14645 };

        const compareResult1 = service.compareLocationCnC(entity1, entity2);
        const compareResult2 = service.compareLocationCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 3212 };
        const entity2 = { id: 3212 };

        const compareResult1 = service.compareLocationCnC(entity1, entity2);
        const compareResult2 = service.compareLocationCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
