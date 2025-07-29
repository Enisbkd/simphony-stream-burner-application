import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IHierarchieCnC } from '../hierarchie-cn-c.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../hierarchie-cn-c.test-samples';

import { HierarchieCnCService } from './hierarchie-cn-c.service';

const requireRestSample: IHierarchieCnC = {
  ...sampleWithRequiredData,
};

describe('HierarchieCnC Service', () => {
  let service: HierarchieCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IHierarchieCnC | IHierarchieCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(HierarchieCnCService);
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

    it('should create a HierarchieCnC', () => {
      const hierarchieCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(hierarchieCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HierarchieCnC', () => {
      const hierarchieCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(hierarchieCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HierarchieCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HierarchieCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HierarchieCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHierarchieCnCToCollectionIfMissing', () => {
      it('should add a HierarchieCnC to an empty array', () => {
        const hierarchieCnC: IHierarchieCnC = sampleWithRequiredData;
        expectedResult = service.addHierarchieCnCToCollectionIfMissing([], hierarchieCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hierarchieCnC);
      });

      it('should not add a HierarchieCnC to an array that contains it', () => {
        const hierarchieCnC: IHierarchieCnC = sampleWithRequiredData;
        const hierarchieCnCCollection: IHierarchieCnC[] = [
          {
            ...hierarchieCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHierarchieCnCToCollectionIfMissing(hierarchieCnCCollection, hierarchieCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HierarchieCnC to an array that doesn't contain it", () => {
        const hierarchieCnC: IHierarchieCnC = sampleWithRequiredData;
        const hierarchieCnCCollection: IHierarchieCnC[] = [sampleWithPartialData];
        expectedResult = service.addHierarchieCnCToCollectionIfMissing(hierarchieCnCCollection, hierarchieCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hierarchieCnC);
      });

      it('should add only unique HierarchieCnC to an array', () => {
        const hierarchieCnCArray: IHierarchieCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hierarchieCnCCollection: IHierarchieCnC[] = [sampleWithRequiredData];
        expectedResult = service.addHierarchieCnCToCollectionIfMissing(hierarchieCnCCollection, ...hierarchieCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hierarchieCnC: IHierarchieCnC = sampleWithRequiredData;
        const hierarchieCnC2: IHierarchieCnC = sampleWithPartialData;
        expectedResult = service.addHierarchieCnCToCollectionIfMissing([], hierarchieCnC, hierarchieCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hierarchieCnC);
        expect(expectedResult).toContain(hierarchieCnC2);
      });

      it('should accept null and undefined values', () => {
        const hierarchieCnC: IHierarchieCnC = sampleWithRequiredData;
        expectedResult = service.addHierarchieCnCToCollectionIfMissing([], null, hierarchieCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hierarchieCnC);
      });

      it('should return initial array if no HierarchieCnC is added', () => {
        const hierarchieCnCCollection: IHierarchieCnC[] = [sampleWithRequiredData];
        expectedResult = service.addHierarchieCnCToCollectionIfMissing(hierarchieCnCCollection, undefined, null);
        expect(expectedResult).toEqual(hierarchieCnCCollection);
      });
    });

    describe('compareHierarchieCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHierarchieCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 21190 };
        const entity2 = null;

        const compareResult1 = service.compareHierarchieCnC(entity1, entity2);
        const compareResult2 = service.compareHierarchieCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 21190 };
        const entity2 = { id: 15751 };

        const compareResult1 = service.compareHierarchieCnC(entity1, entity2);
        const compareResult2 = service.compareHierarchieCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 21190 };
        const entity2 = { id: 21190 };

        const compareResult1 = service.compareHierarchieCnC(entity1, entity2);
        const compareResult2 = service.compareHierarchieCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
