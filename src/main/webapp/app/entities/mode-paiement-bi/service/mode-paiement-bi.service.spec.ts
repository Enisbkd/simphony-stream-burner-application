import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IModePaiementBI } from '../mode-paiement-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../mode-paiement-bi.test-samples';

import { ModePaiementBIService } from './mode-paiement-bi.service';

const requireRestSample: IModePaiementBI = {
  ...sampleWithRequiredData,
};

describe('ModePaiementBI Service', () => {
  let service: ModePaiementBIService;
  let httpMock: HttpTestingController;
  let expectedResult: IModePaiementBI | IModePaiementBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ModePaiementBIService);
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

    it('should create a ModePaiementBI', () => {
      const modePaiementBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(modePaiementBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ModePaiementBI', () => {
      const modePaiementBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(modePaiementBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ModePaiementBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ModePaiementBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ModePaiementBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addModePaiementBIToCollectionIfMissing', () => {
      it('should add a ModePaiementBI to an empty array', () => {
        const modePaiementBI: IModePaiementBI = sampleWithRequiredData;
        expectedResult = service.addModePaiementBIToCollectionIfMissing([], modePaiementBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modePaiementBI);
      });

      it('should not add a ModePaiementBI to an array that contains it', () => {
        const modePaiementBI: IModePaiementBI = sampleWithRequiredData;
        const modePaiementBICollection: IModePaiementBI[] = [
          {
            ...modePaiementBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addModePaiementBIToCollectionIfMissing(modePaiementBICollection, modePaiementBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ModePaiementBI to an array that doesn't contain it", () => {
        const modePaiementBI: IModePaiementBI = sampleWithRequiredData;
        const modePaiementBICollection: IModePaiementBI[] = [sampleWithPartialData];
        expectedResult = service.addModePaiementBIToCollectionIfMissing(modePaiementBICollection, modePaiementBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modePaiementBI);
      });

      it('should add only unique ModePaiementBI to an array', () => {
        const modePaiementBIArray: IModePaiementBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const modePaiementBICollection: IModePaiementBI[] = [sampleWithRequiredData];
        expectedResult = service.addModePaiementBIToCollectionIfMissing(modePaiementBICollection, ...modePaiementBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modePaiementBI: IModePaiementBI = sampleWithRequiredData;
        const modePaiementBI2: IModePaiementBI = sampleWithPartialData;
        expectedResult = service.addModePaiementBIToCollectionIfMissing([], modePaiementBI, modePaiementBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modePaiementBI);
        expect(expectedResult).toContain(modePaiementBI2);
      });

      it('should accept null and undefined values', () => {
        const modePaiementBI: IModePaiementBI = sampleWithRequiredData;
        expectedResult = service.addModePaiementBIToCollectionIfMissing([], null, modePaiementBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modePaiementBI);
      });

      it('should return initial array if no ModePaiementBI is added', () => {
        const modePaiementBICollection: IModePaiementBI[] = [sampleWithRequiredData];
        expectedResult = service.addModePaiementBIToCollectionIfMissing(modePaiementBICollection, undefined, null);
        expect(expectedResult).toEqual(modePaiementBICollection);
      });
    });

    describe('compareModePaiementBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareModePaiementBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 25413 };
        const entity2 = null;

        const compareResult1 = service.compareModePaiementBI(entity1, entity2);
        const compareResult2 = service.compareModePaiementBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 25413 };
        const entity2 = { id: 32002 };

        const compareResult1 = service.compareModePaiementBI(entity1, entity2);
        const compareResult2 = service.compareModePaiementBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 25413 };
        const entity2 = { id: 25413 };

        const compareResult1 = service.compareModePaiementBI(entity1, entity2);
        const compareResult2 = service.compareModePaiementBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
