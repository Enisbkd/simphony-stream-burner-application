import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITaxeClassTrans } from '../taxe-class-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../taxe-class-trans.test-samples';

import { TaxeClassTransService } from './taxe-class-trans.service';

const requireRestSample: ITaxeClassTrans = {
  ...sampleWithRequiredData,
};

describe('TaxeClassTrans Service', () => {
  let service: TaxeClassTransService;
  let httpMock: HttpTestingController;
  let expectedResult: ITaxeClassTrans | ITaxeClassTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TaxeClassTransService);
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

    it('should create a TaxeClassTrans', () => {
      const taxeClassTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(taxeClassTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TaxeClassTrans', () => {
      const taxeClassTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(taxeClassTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TaxeClassTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TaxeClassTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TaxeClassTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTaxeClassTransToCollectionIfMissing', () => {
      it('should add a TaxeClassTrans to an empty array', () => {
        const taxeClassTrans: ITaxeClassTrans = sampleWithRequiredData;
        expectedResult = service.addTaxeClassTransToCollectionIfMissing([], taxeClassTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxeClassTrans);
      });

      it('should not add a TaxeClassTrans to an array that contains it', () => {
        const taxeClassTrans: ITaxeClassTrans = sampleWithRequiredData;
        const taxeClassTransCollection: ITaxeClassTrans[] = [
          {
            ...taxeClassTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTaxeClassTransToCollectionIfMissing(taxeClassTransCollection, taxeClassTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TaxeClassTrans to an array that doesn't contain it", () => {
        const taxeClassTrans: ITaxeClassTrans = sampleWithRequiredData;
        const taxeClassTransCollection: ITaxeClassTrans[] = [sampleWithPartialData];
        expectedResult = service.addTaxeClassTransToCollectionIfMissing(taxeClassTransCollection, taxeClassTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxeClassTrans);
      });

      it('should add only unique TaxeClassTrans to an array', () => {
        const taxeClassTransArray: ITaxeClassTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const taxeClassTransCollection: ITaxeClassTrans[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeClassTransToCollectionIfMissing(taxeClassTransCollection, ...taxeClassTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const taxeClassTrans: ITaxeClassTrans = sampleWithRequiredData;
        const taxeClassTrans2: ITaxeClassTrans = sampleWithPartialData;
        expectedResult = service.addTaxeClassTransToCollectionIfMissing([], taxeClassTrans, taxeClassTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxeClassTrans);
        expect(expectedResult).toContain(taxeClassTrans2);
      });

      it('should accept null and undefined values', () => {
        const taxeClassTrans: ITaxeClassTrans = sampleWithRequiredData;
        expectedResult = service.addTaxeClassTransToCollectionIfMissing([], null, taxeClassTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxeClassTrans);
      });

      it('should return initial array if no TaxeClassTrans is added', () => {
        const taxeClassTransCollection: ITaxeClassTrans[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeClassTransToCollectionIfMissing(taxeClassTransCollection, undefined, null);
        expect(expectedResult).toEqual(taxeClassTransCollection);
      });
    });

    describe('compareTaxeClassTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTaxeClassTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 32691 };
        const entity2 = null;

        const compareResult1 = service.compareTaxeClassTrans(entity1, entity2);
        const compareResult2 = service.compareTaxeClassTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 32691 };
        const entity2 = { id: 30772 };

        const compareResult1 = service.compareTaxeClassTrans(entity1, entity2);
        const compareResult2 = service.compareTaxeClassTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 32691 };
        const entity2 = { id: 32691 };

        const compareResult1 = service.compareTaxeClassTrans(entity1, entity2);
        const compareResult2 = service.compareTaxeClassTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
