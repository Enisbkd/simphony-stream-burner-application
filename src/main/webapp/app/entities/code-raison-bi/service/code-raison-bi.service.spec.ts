import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICodeRaisonBI } from '../code-raison-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../code-raison-bi.test-samples';

import { CodeRaisonBIService } from './code-raison-bi.service';

const requireRestSample: ICodeRaisonBI = {
  ...sampleWithRequiredData,
};

describe('CodeRaisonBI Service', () => {
  let service: CodeRaisonBIService;
  let httpMock: HttpTestingController;
  let expectedResult: ICodeRaisonBI | ICodeRaisonBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CodeRaisonBIService);
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

    it('should create a CodeRaisonBI', () => {
      const codeRaisonBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(codeRaisonBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CodeRaisonBI', () => {
      const codeRaisonBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(codeRaisonBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CodeRaisonBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CodeRaisonBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CodeRaisonBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCodeRaisonBIToCollectionIfMissing', () => {
      it('should add a CodeRaisonBI to an empty array', () => {
        const codeRaisonBI: ICodeRaisonBI = sampleWithRequiredData;
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing([], codeRaisonBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeRaisonBI);
      });

      it('should not add a CodeRaisonBI to an array that contains it', () => {
        const codeRaisonBI: ICodeRaisonBI = sampleWithRequiredData;
        const codeRaisonBICollection: ICodeRaisonBI[] = [
          {
            ...codeRaisonBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing(codeRaisonBICollection, codeRaisonBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CodeRaisonBI to an array that doesn't contain it", () => {
        const codeRaisonBI: ICodeRaisonBI = sampleWithRequiredData;
        const codeRaisonBICollection: ICodeRaisonBI[] = [sampleWithPartialData];
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing(codeRaisonBICollection, codeRaisonBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeRaisonBI);
      });

      it('should add only unique CodeRaisonBI to an array', () => {
        const codeRaisonBIArray: ICodeRaisonBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const codeRaisonBICollection: ICodeRaisonBI[] = [sampleWithRequiredData];
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing(codeRaisonBICollection, ...codeRaisonBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const codeRaisonBI: ICodeRaisonBI = sampleWithRequiredData;
        const codeRaisonBI2: ICodeRaisonBI = sampleWithPartialData;
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing([], codeRaisonBI, codeRaisonBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeRaisonBI);
        expect(expectedResult).toContain(codeRaisonBI2);
      });

      it('should accept null and undefined values', () => {
        const codeRaisonBI: ICodeRaisonBI = sampleWithRequiredData;
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing([], null, codeRaisonBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeRaisonBI);
      });

      it('should return initial array if no CodeRaisonBI is added', () => {
        const codeRaisonBICollection: ICodeRaisonBI[] = [sampleWithRequiredData];
        expectedResult = service.addCodeRaisonBIToCollectionIfMissing(codeRaisonBICollection, undefined, null);
        expect(expectedResult).toEqual(codeRaisonBICollection);
      });
    });

    describe('compareCodeRaisonBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCodeRaisonBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 11250 };
        const entity2 = null;

        const compareResult1 = service.compareCodeRaisonBI(entity1, entity2);
        const compareResult2 = service.compareCodeRaisonBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 11250 };
        const entity2 = { id: 369 };

        const compareResult1 = service.compareCodeRaisonBI(entity1, entity2);
        const compareResult2 = service.compareCodeRaisonBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 11250 };
        const entity2 = { id: 11250 };

        const compareResult1 = service.compareCodeRaisonBI(entity1, entity2);
        const compareResult2 = service.compareCodeRaisonBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
