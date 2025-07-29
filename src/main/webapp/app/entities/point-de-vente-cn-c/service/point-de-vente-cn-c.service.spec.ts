import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../point-de-vente-cn-c.test-samples';

import { PointDeVenteCnCService } from './point-de-vente-cn-c.service';

const requireRestSample: IPointDeVenteCnC = {
  ...sampleWithRequiredData,
};

describe('PointDeVenteCnC Service', () => {
  let service: PointDeVenteCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IPointDeVenteCnC | IPointDeVenteCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PointDeVenteCnCService);
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

    it('should create a PointDeVenteCnC', () => {
      const pointDeVenteCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pointDeVenteCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointDeVenteCnC', () => {
      const pointDeVenteCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pointDeVenteCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PointDeVenteCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PointDeVenteCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PointDeVenteCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPointDeVenteCnCToCollectionIfMissing', () => {
      it('should add a PointDeVenteCnC to an empty array', () => {
        const pointDeVenteCnC: IPointDeVenteCnC = sampleWithRequiredData;
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing([], pointDeVenteCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointDeVenteCnC);
      });

      it('should not add a PointDeVenteCnC to an array that contains it', () => {
        const pointDeVenteCnC: IPointDeVenteCnC = sampleWithRequiredData;
        const pointDeVenteCnCCollection: IPointDeVenteCnC[] = [
          {
            ...pointDeVenteCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing(pointDeVenteCnCCollection, pointDeVenteCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointDeVenteCnC to an array that doesn't contain it", () => {
        const pointDeVenteCnC: IPointDeVenteCnC = sampleWithRequiredData;
        const pointDeVenteCnCCollection: IPointDeVenteCnC[] = [sampleWithPartialData];
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing(pointDeVenteCnCCollection, pointDeVenteCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointDeVenteCnC);
      });

      it('should add only unique PointDeVenteCnC to an array', () => {
        const pointDeVenteCnCArray: IPointDeVenteCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pointDeVenteCnCCollection: IPointDeVenteCnC[] = [sampleWithRequiredData];
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing(pointDeVenteCnCCollection, ...pointDeVenteCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointDeVenteCnC: IPointDeVenteCnC = sampleWithRequiredData;
        const pointDeVenteCnC2: IPointDeVenteCnC = sampleWithPartialData;
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing([], pointDeVenteCnC, pointDeVenteCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointDeVenteCnC);
        expect(expectedResult).toContain(pointDeVenteCnC2);
      });

      it('should accept null and undefined values', () => {
        const pointDeVenteCnC: IPointDeVenteCnC = sampleWithRequiredData;
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing([], null, pointDeVenteCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointDeVenteCnC);
      });

      it('should return initial array if no PointDeVenteCnC is added', () => {
        const pointDeVenteCnCCollection: IPointDeVenteCnC[] = [sampleWithRequiredData];
        expectedResult = service.addPointDeVenteCnCToCollectionIfMissing(pointDeVenteCnCCollection, undefined, null);
        expect(expectedResult).toEqual(pointDeVenteCnCCollection);
      });
    });

    describe('comparePointDeVenteCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePointDeVenteCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 11208 };
        const entity2 = null;

        const compareResult1 = service.comparePointDeVenteCnC(entity1, entity2);
        const compareResult2 = service.comparePointDeVenteCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 11208 };
        const entity2 = { id: 10111 };

        const compareResult1 = service.comparePointDeVenteCnC(entity1, entity2);
        const compareResult2 = service.comparePointDeVenteCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 11208 };
        const entity2 = { id: 11208 };

        const compareResult1 = service.comparePointDeVenteCnC(entity1, entity2);
        const compareResult2 = service.comparePointDeVenteCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
