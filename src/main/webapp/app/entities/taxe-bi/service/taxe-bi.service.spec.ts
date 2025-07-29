import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITaxeBI } from '../taxe-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../taxe-bi.test-samples';

import { TaxeBIService } from './taxe-bi.service';

const requireRestSample: ITaxeBI = {
  ...sampleWithRequiredData,
};

describe('TaxeBI Service', () => {
  let service: TaxeBIService;
  let httpMock: HttpTestingController;
  let expectedResult: ITaxeBI | ITaxeBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TaxeBIService);
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

    it('should create a TaxeBI', () => {
      const taxeBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(taxeBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TaxeBI', () => {
      const taxeBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(taxeBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TaxeBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TaxeBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TaxeBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTaxeBIToCollectionIfMissing', () => {
      it('should add a TaxeBI to an empty array', () => {
        const taxeBI: ITaxeBI = sampleWithRequiredData;
        expectedResult = service.addTaxeBIToCollectionIfMissing([], taxeBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxeBI);
      });

      it('should not add a TaxeBI to an array that contains it', () => {
        const taxeBI: ITaxeBI = sampleWithRequiredData;
        const taxeBICollection: ITaxeBI[] = [
          {
            ...taxeBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTaxeBIToCollectionIfMissing(taxeBICollection, taxeBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TaxeBI to an array that doesn't contain it", () => {
        const taxeBI: ITaxeBI = sampleWithRequiredData;
        const taxeBICollection: ITaxeBI[] = [sampleWithPartialData];
        expectedResult = service.addTaxeBIToCollectionIfMissing(taxeBICollection, taxeBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxeBI);
      });

      it('should add only unique TaxeBI to an array', () => {
        const taxeBIArray: ITaxeBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const taxeBICollection: ITaxeBI[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeBIToCollectionIfMissing(taxeBICollection, ...taxeBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const taxeBI: ITaxeBI = sampleWithRequiredData;
        const taxeBI2: ITaxeBI = sampleWithPartialData;
        expectedResult = service.addTaxeBIToCollectionIfMissing([], taxeBI, taxeBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxeBI);
        expect(expectedResult).toContain(taxeBI2);
      });

      it('should accept null and undefined values', () => {
        const taxeBI: ITaxeBI = sampleWithRequiredData;
        expectedResult = service.addTaxeBIToCollectionIfMissing([], null, taxeBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxeBI);
      });

      it('should return initial array if no TaxeBI is added', () => {
        const taxeBICollection: ITaxeBI[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeBIToCollectionIfMissing(taxeBICollection, undefined, null);
        expect(expectedResult).toEqual(taxeBICollection);
      });
    });

    describe('compareTaxeBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTaxeBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18086 };
        const entity2 = null;

        const compareResult1 = service.compareTaxeBI(entity1, entity2);
        const compareResult2 = service.compareTaxeBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18086 };
        const entity2 = { id: 3761 };

        const compareResult1 = service.compareTaxeBI(entity1, entity2);
        const compareResult2 = service.compareTaxeBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18086 };
        const entity2 = { id: 18086 };

        const compareResult1 = service.compareTaxeBI(entity1, entity2);
        const compareResult2 = service.compareTaxeBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
