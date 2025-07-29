import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFamilyGroupCnC } from '../family-group-cn-c.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../family-group-cn-c.test-samples';

import { FamilyGroupCnCService } from './family-group-cn-c.service';

const requireRestSample: IFamilyGroupCnC = {
  ...sampleWithRequiredData,
};

describe('FamilyGroupCnC Service', () => {
  let service: FamilyGroupCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IFamilyGroupCnC | IFamilyGroupCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FamilyGroupCnCService);
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

    it('should create a FamilyGroupCnC', () => {
      const familyGroupCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(familyGroupCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FamilyGroupCnC', () => {
      const familyGroupCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(familyGroupCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FamilyGroupCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FamilyGroupCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FamilyGroupCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFamilyGroupCnCToCollectionIfMissing', () => {
      it('should add a FamilyGroupCnC to an empty array', () => {
        const familyGroupCnC: IFamilyGroupCnC = sampleWithRequiredData;
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing([], familyGroupCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(familyGroupCnC);
      });

      it('should not add a FamilyGroupCnC to an array that contains it', () => {
        const familyGroupCnC: IFamilyGroupCnC = sampleWithRequiredData;
        const familyGroupCnCCollection: IFamilyGroupCnC[] = [
          {
            ...familyGroupCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing(familyGroupCnCCollection, familyGroupCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FamilyGroupCnC to an array that doesn't contain it", () => {
        const familyGroupCnC: IFamilyGroupCnC = sampleWithRequiredData;
        const familyGroupCnCCollection: IFamilyGroupCnC[] = [sampleWithPartialData];
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing(familyGroupCnCCollection, familyGroupCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(familyGroupCnC);
      });

      it('should add only unique FamilyGroupCnC to an array', () => {
        const familyGroupCnCArray: IFamilyGroupCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const familyGroupCnCCollection: IFamilyGroupCnC[] = [sampleWithRequiredData];
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing(familyGroupCnCCollection, ...familyGroupCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const familyGroupCnC: IFamilyGroupCnC = sampleWithRequiredData;
        const familyGroupCnC2: IFamilyGroupCnC = sampleWithPartialData;
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing([], familyGroupCnC, familyGroupCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(familyGroupCnC);
        expect(expectedResult).toContain(familyGroupCnC2);
      });

      it('should accept null and undefined values', () => {
        const familyGroupCnC: IFamilyGroupCnC = sampleWithRequiredData;
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing([], null, familyGroupCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(familyGroupCnC);
      });

      it('should return initial array if no FamilyGroupCnC is added', () => {
        const familyGroupCnCCollection: IFamilyGroupCnC[] = [sampleWithRequiredData];
        expectedResult = service.addFamilyGroupCnCToCollectionIfMissing(familyGroupCnCCollection, undefined, null);
        expect(expectedResult).toEqual(familyGroupCnCCollection);
      });
    });

    describe('compareFamilyGroupCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFamilyGroupCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 16235 };
        const entity2 = null;

        const compareResult1 = service.compareFamilyGroupCnC(entity1, entity2);
        const compareResult2 = service.compareFamilyGroupCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 16235 };
        const entity2 = { id: 8835 };

        const compareResult1 = service.compareFamilyGroupCnC(entity1, entity2);
        const compareResult2 = service.compareFamilyGroupCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 16235 };
        const entity2 = { id: 16235 };

        const compareResult1 = service.compareFamilyGroupCnC(entity1, entity2);
        const compareResult2 = service.compareFamilyGroupCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
