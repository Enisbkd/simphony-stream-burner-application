import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPointDeVenteTrans } from '../point-de-vente-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../point-de-vente-trans.test-samples';

import { PointDeVenteTransService } from './point-de-vente-trans.service';

const requireRestSample: IPointDeVenteTrans = {
  ...sampleWithRequiredData,
};

describe('PointDeVenteTrans Service', () => {
  let service: PointDeVenteTransService;
  let httpMock: HttpTestingController;
  let expectedResult: IPointDeVenteTrans | IPointDeVenteTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PointDeVenteTransService);
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

    it('should create a PointDeVenteTrans', () => {
      const pointDeVenteTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pointDeVenteTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointDeVenteTrans', () => {
      const pointDeVenteTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pointDeVenteTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PointDeVenteTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PointDeVenteTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PointDeVenteTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPointDeVenteTransToCollectionIfMissing', () => {
      it('should add a PointDeVenteTrans to an empty array', () => {
        const pointDeVenteTrans: IPointDeVenteTrans = sampleWithRequiredData;
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing([], pointDeVenteTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointDeVenteTrans);
      });

      it('should not add a PointDeVenteTrans to an array that contains it', () => {
        const pointDeVenteTrans: IPointDeVenteTrans = sampleWithRequiredData;
        const pointDeVenteTransCollection: IPointDeVenteTrans[] = [
          {
            ...pointDeVenteTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing(pointDeVenteTransCollection, pointDeVenteTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointDeVenteTrans to an array that doesn't contain it", () => {
        const pointDeVenteTrans: IPointDeVenteTrans = sampleWithRequiredData;
        const pointDeVenteTransCollection: IPointDeVenteTrans[] = [sampleWithPartialData];
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing(pointDeVenteTransCollection, pointDeVenteTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointDeVenteTrans);
      });

      it('should add only unique PointDeVenteTrans to an array', () => {
        const pointDeVenteTransArray: IPointDeVenteTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pointDeVenteTransCollection: IPointDeVenteTrans[] = [sampleWithRequiredData];
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing(pointDeVenteTransCollection, ...pointDeVenteTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointDeVenteTrans: IPointDeVenteTrans = sampleWithRequiredData;
        const pointDeVenteTrans2: IPointDeVenteTrans = sampleWithPartialData;
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing([], pointDeVenteTrans, pointDeVenteTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointDeVenteTrans);
        expect(expectedResult).toContain(pointDeVenteTrans2);
      });

      it('should accept null and undefined values', () => {
        const pointDeVenteTrans: IPointDeVenteTrans = sampleWithRequiredData;
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing([], null, pointDeVenteTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointDeVenteTrans);
      });

      it('should return initial array if no PointDeVenteTrans is added', () => {
        const pointDeVenteTransCollection: IPointDeVenteTrans[] = [sampleWithRequiredData];
        expectedResult = service.addPointDeVenteTransToCollectionIfMissing(pointDeVenteTransCollection, undefined, null);
        expect(expectedResult).toEqual(pointDeVenteTransCollection);
      });
    });

    describe('comparePointDeVenteTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePointDeVenteTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 3768 };
        const entity2 = null;

        const compareResult1 = service.comparePointDeVenteTrans(entity1, entity2);
        const compareResult2 = service.comparePointDeVenteTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 3768 };
        const entity2 = { id: 31903 };

        const compareResult1 = service.comparePointDeVenteTrans(entity1, entity2);
        const compareResult2 = service.comparePointDeVenteTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 3768 };
        const entity2 = { id: 3768 };

        const compareResult1 = service.comparePointDeVenteTrans(entity1, entity2);
        const compareResult2 = service.comparePointDeVenteTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
