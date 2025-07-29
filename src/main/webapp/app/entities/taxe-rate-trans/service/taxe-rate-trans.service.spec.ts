import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITaxeRateTrans } from '../taxe-rate-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../taxe-rate-trans.test-samples';

import { TaxeRateTransService } from './taxe-rate-trans.service';

const requireRestSample: ITaxeRateTrans = {
  ...sampleWithRequiredData,
};

describe('TaxeRateTrans Service', () => {
  let service: TaxeRateTransService;
  let httpMock: HttpTestingController;
  let expectedResult: ITaxeRateTrans | ITaxeRateTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TaxeRateTransService);
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

    it('should create a TaxeRateTrans', () => {
      const taxeRateTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(taxeRateTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TaxeRateTrans', () => {
      const taxeRateTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(taxeRateTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TaxeRateTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TaxeRateTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TaxeRateTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTaxeRateTransToCollectionIfMissing', () => {
      it('should add a TaxeRateTrans to an empty array', () => {
        const taxeRateTrans: ITaxeRateTrans = sampleWithRequiredData;
        expectedResult = service.addTaxeRateTransToCollectionIfMissing([], taxeRateTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxeRateTrans);
      });

      it('should not add a TaxeRateTrans to an array that contains it', () => {
        const taxeRateTrans: ITaxeRateTrans = sampleWithRequiredData;
        const taxeRateTransCollection: ITaxeRateTrans[] = [
          {
            ...taxeRateTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTaxeRateTransToCollectionIfMissing(taxeRateTransCollection, taxeRateTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TaxeRateTrans to an array that doesn't contain it", () => {
        const taxeRateTrans: ITaxeRateTrans = sampleWithRequiredData;
        const taxeRateTransCollection: ITaxeRateTrans[] = [sampleWithPartialData];
        expectedResult = service.addTaxeRateTransToCollectionIfMissing(taxeRateTransCollection, taxeRateTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxeRateTrans);
      });

      it('should add only unique TaxeRateTrans to an array', () => {
        const taxeRateTransArray: ITaxeRateTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const taxeRateTransCollection: ITaxeRateTrans[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeRateTransToCollectionIfMissing(taxeRateTransCollection, ...taxeRateTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const taxeRateTrans: ITaxeRateTrans = sampleWithRequiredData;
        const taxeRateTrans2: ITaxeRateTrans = sampleWithPartialData;
        expectedResult = service.addTaxeRateTransToCollectionIfMissing([], taxeRateTrans, taxeRateTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxeRateTrans);
        expect(expectedResult).toContain(taxeRateTrans2);
      });

      it('should accept null and undefined values', () => {
        const taxeRateTrans: ITaxeRateTrans = sampleWithRequiredData;
        expectedResult = service.addTaxeRateTransToCollectionIfMissing([], null, taxeRateTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxeRateTrans);
      });

      it('should return initial array if no TaxeRateTrans is added', () => {
        const taxeRateTransCollection: ITaxeRateTrans[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeRateTransToCollectionIfMissing(taxeRateTransCollection, undefined, null);
        expect(expectedResult).toEqual(taxeRateTransCollection);
      });
    });

    describe('compareTaxeRateTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTaxeRateTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 7322 };
        const entity2 = null;

        const compareResult1 = service.compareTaxeRateTrans(entity1, entity2);
        const compareResult2 = service.compareTaxeRateTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 7322 };
        const entity2 = { id: 8059 };

        const compareResult1 = service.compareTaxeRateTrans(entity1, entity2);
        const compareResult2 = service.compareTaxeRateTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 7322 };
        const entity2 = { id: 7322 };

        const compareResult1 = service.compareTaxeRateTrans(entity1, entity2);
        const compareResult2 = service.compareTaxeRateTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
