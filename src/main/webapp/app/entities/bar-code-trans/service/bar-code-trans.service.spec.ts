import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IBarCodeTrans } from '../bar-code-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../bar-code-trans.test-samples';

import { BarCodeTransService } from './bar-code-trans.service';

const requireRestSample: IBarCodeTrans = {
  ...sampleWithRequiredData,
};

describe('BarCodeTrans Service', () => {
  let service: BarCodeTransService;
  let httpMock: HttpTestingController;
  let expectedResult: IBarCodeTrans | IBarCodeTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BarCodeTransService);
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

    it('should create a BarCodeTrans', () => {
      const barCodeTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(barCodeTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BarCodeTrans', () => {
      const barCodeTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(barCodeTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BarCodeTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BarCodeTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BarCodeTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBarCodeTransToCollectionIfMissing', () => {
      it('should add a BarCodeTrans to an empty array', () => {
        const barCodeTrans: IBarCodeTrans = sampleWithRequiredData;
        expectedResult = service.addBarCodeTransToCollectionIfMissing([], barCodeTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(barCodeTrans);
      });

      it('should not add a BarCodeTrans to an array that contains it', () => {
        const barCodeTrans: IBarCodeTrans = sampleWithRequiredData;
        const barCodeTransCollection: IBarCodeTrans[] = [
          {
            ...barCodeTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBarCodeTransToCollectionIfMissing(barCodeTransCollection, barCodeTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BarCodeTrans to an array that doesn't contain it", () => {
        const barCodeTrans: IBarCodeTrans = sampleWithRequiredData;
        const barCodeTransCollection: IBarCodeTrans[] = [sampleWithPartialData];
        expectedResult = service.addBarCodeTransToCollectionIfMissing(barCodeTransCollection, barCodeTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(barCodeTrans);
      });

      it('should add only unique BarCodeTrans to an array', () => {
        const barCodeTransArray: IBarCodeTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const barCodeTransCollection: IBarCodeTrans[] = [sampleWithRequiredData];
        expectedResult = service.addBarCodeTransToCollectionIfMissing(barCodeTransCollection, ...barCodeTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const barCodeTrans: IBarCodeTrans = sampleWithRequiredData;
        const barCodeTrans2: IBarCodeTrans = sampleWithPartialData;
        expectedResult = service.addBarCodeTransToCollectionIfMissing([], barCodeTrans, barCodeTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(barCodeTrans);
        expect(expectedResult).toContain(barCodeTrans2);
      });

      it('should accept null and undefined values', () => {
        const barCodeTrans: IBarCodeTrans = sampleWithRequiredData;
        expectedResult = service.addBarCodeTransToCollectionIfMissing([], null, barCodeTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(barCodeTrans);
      });

      it('should return initial array if no BarCodeTrans is added', () => {
        const barCodeTransCollection: IBarCodeTrans[] = [sampleWithRequiredData];
        expectedResult = service.addBarCodeTransToCollectionIfMissing(barCodeTransCollection, undefined, null);
        expect(expectedResult).toEqual(barCodeTransCollection);
      });
    });

    describe('compareBarCodeTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBarCodeTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 19267 };
        const entity2 = null;

        const compareResult1 = service.compareBarCodeTrans(entity1, entity2);
        const compareResult2 = service.compareBarCodeTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 19267 };
        const entity2 = { id: 9235 };

        const compareResult1 = service.compareBarCodeTrans(entity1, entity2);
        const compareResult2 = service.compareBarCodeTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 19267 };
        const entity2 = { id: 19267 };

        const compareResult1 = service.compareBarCodeTrans(entity1, entity2);
        const compareResult2 = service.compareBarCodeTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
