import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISocieteTrans } from '../societe-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../societe-trans.test-samples';

import { SocieteTransService } from './societe-trans.service';

const requireRestSample: ISocieteTrans = {
  ...sampleWithRequiredData,
};

describe('SocieteTrans Service', () => {
  let service: SocieteTransService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocieteTrans | ISocieteTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SocieteTransService);
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

    it('should create a SocieteTrans', () => {
      const societeTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societeTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocieteTrans', () => {
      const societeTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societeTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocieteTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocieteTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocieteTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSocieteTransToCollectionIfMissing', () => {
      it('should add a SocieteTrans to an empty array', () => {
        const societeTrans: ISocieteTrans = sampleWithRequiredData;
        expectedResult = service.addSocieteTransToCollectionIfMissing([], societeTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societeTrans);
      });

      it('should not add a SocieteTrans to an array that contains it', () => {
        const societeTrans: ISocieteTrans = sampleWithRequiredData;
        const societeTransCollection: ISocieteTrans[] = [
          {
            ...societeTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocieteTransToCollectionIfMissing(societeTransCollection, societeTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocieteTrans to an array that doesn't contain it", () => {
        const societeTrans: ISocieteTrans = sampleWithRequiredData;
        const societeTransCollection: ISocieteTrans[] = [sampleWithPartialData];
        expectedResult = service.addSocieteTransToCollectionIfMissing(societeTransCollection, societeTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societeTrans);
      });

      it('should add only unique SocieteTrans to an array', () => {
        const societeTransArray: ISocieteTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societeTransCollection: ISocieteTrans[] = [sampleWithRequiredData];
        expectedResult = service.addSocieteTransToCollectionIfMissing(societeTransCollection, ...societeTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societeTrans: ISocieteTrans = sampleWithRequiredData;
        const societeTrans2: ISocieteTrans = sampleWithPartialData;
        expectedResult = service.addSocieteTransToCollectionIfMissing([], societeTrans, societeTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societeTrans);
        expect(expectedResult).toContain(societeTrans2);
      });

      it('should accept null and undefined values', () => {
        const societeTrans: ISocieteTrans = sampleWithRequiredData;
        expectedResult = service.addSocieteTransToCollectionIfMissing([], null, societeTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societeTrans);
      });

      it('should return initial array if no SocieteTrans is added', () => {
        const societeTransCollection: ISocieteTrans[] = [sampleWithRequiredData];
        expectedResult = service.addSocieteTransToCollectionIfMissing(societeTransCollection, undefined, null);
        expect(expectedResult).toEqual(societeTransCollection);
      });
    });

    describe('compareSocieteTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocieteTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 1464 };
        const entity2 = null;

        const compareResult1 = service.compareSocieteTrans(entity1, entity2);
        const compareResult2 = service.compareSocieteTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 1464 };
        const entity2 = { id: 10458 };

        const compareResult1 = service.compareSocieteTrans(entity1, entity2);
        const compareResult2 = service.compareSocieteTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 1464 };
        const entity2 = { id: 1464 };

        const compareResult1 = service.compareSocieteTrans(entity1, entity2);
        const compareResult2 = service.compareSocieteTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
