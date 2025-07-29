import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMajorGroupCnC } from '../major-group-cn-c.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../major-group-cn-c.test-samples';

import { MajorGroupCnCService } from './major-group-cn-c.service';

const requireRestSample: IMajorGroupCnC = {
  ...sampleWithRequiredData,
};

describe('MajorGroupCnC Service', () => {
  let service: MajorGroupCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IMajorGroupCnC | IMajorGroupCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MajorGroupCnCService);
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

    it('should create a MajorGroupCnC', () => {
      const majorGroupCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(majorGroupCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MajorGroupCnC', () => {
      const majorGroupCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(majorGroupCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MajorGroupCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MajorGroupCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MajorGroupCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMajorGroupCnCToCollectionIfMissing', () => {
      it('should add a MajorGroupCnC to an empty array', () => {
        const majorGroupCnC: IMajorGroupCnC = sampleWithRequiredData;
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing([], majorGroupCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(majorGroupCnC);
      });

      it('should not add a MajorGroupCnC to an array that contains it', () => {
        const majorGroupCnC: IMajorGroupCnC = sampleWithRequiredData;
        const majorGroupCnCCollection: IMajorGroupCnC[] = [
          {
            ...majorGroupCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing(majorGroupCnCCollection, majorGroupCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MajorGroupCnC to an array that doesn't contain it", () => {
        const majorGroupCnC: IMajorGroupCnC = sampleWithRequiredData;
        const majorGroupCnCCollection: IMajorGroupCnC[] = [sampleWithPartialData];
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing(majorGroupCnCCollection, majorGroupCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(majorGroupCnC);
      });

      it('should add only unique MajorGroupCnC to an array', () => {
        const majorGroupCnCArray: IMajorGroupCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const majorGroupCnCCollection: IMajorGroupCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing(majorGroupCnCCollection, ...majorGroupCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const majorGroupCnC: IMajorGroupCnC = sampleWithRequiredData;
        const majorGroupCnC2: IMajorGroupCnC = sampleWithPartialData;
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing([], majorGroupCnC, majorGroupCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(majorGroupCnC);
        expect(expectedResult).toContain(majorGroupCnC2);
      });

      it('should accept null and undefined values', () => {
        const majorGroupCnC: IMajorGroupCnC = sampleWithRequiredData;
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing([], null, majorGroupCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(majorGroupCnC);
      });

      it('should return initial array if no MajorGroupCnC is added', () => {
        const majorGroupCnCCollection: IMajorGroupCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMajorGroupCnCToCollectionIfMissing(majorGroupCnCCollection, undefined, null);
        expect(expectedResult).toEqual(majorGroupCnCCollection);
      });
    });

    describe('compareMajorGroupCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMajorGroupCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 17824 };
        const entity2 = null;

        const compareResult1 = service.compareMajorGroupCnC(entity1, entity2);
        const compareResult2 = service.compareMajorGroupCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 17824 };
        const entity2 = { id: 27026 };

        const compareResult1 = service.compareMajorGroupCnC(entity1, entity2);
        const compareResult2 = service.compareMajorGroupCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 17824 };
        const entity2 = { id: 17824 };

        const compareResult1 = service.compareMajorGroupCnC(entity1, entity2);
        const compareResult2 = service.compareMajorGroupCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
