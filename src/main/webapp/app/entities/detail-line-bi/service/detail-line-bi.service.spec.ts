import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDetailLineBI } from '../detail-line-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../detail-line-bi.test-samples';

import { DetailLineBIService, RestDetailLineBI } from './detail-line-bi.service';

const requireRestSample: RestDetailLineBI = {
  ...sampleWithRequiredData,
  detailUTC: sampleWithRequiredData.detailUTC?.toJSON(),
  detailLcl: sampleWithRequiredData.detailLcl?.toJSON(),
};

describe('DetailLineBI Service', () => {
  let service: DetailLineBIService;
  let httpMock: HttpTestingController;
  let expectedResult: IDetailLineBI | IDetailLineBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DetailLineBIService);
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

    it('should create a DetailLineBI', () => {
      const detailLineBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(detailLineBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetailLineBI', () => {
      const detailLineBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(detailLineBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetailLineBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetailLineBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DetailLineBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDetailLineBIToCollectionIfMissing', () => {
      it('should add a DetailLineBI to an empty array', () => {
        const detailLineBI: IDetailLineBI = sampleWithRequiredData;
        expectedResult = service.addDetailLineBIToCollectionIfMissing([], detailLineBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailLineBI);
      });

      it('should not add a DetailLineBI to an array that contains it', () => {
        const detailLineBI: IDetailLineBI = sampleWithRequiredData;
        const detailLineBICollection: IDetailLineBI[] = [
          {
            ...detailLineBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDetailLineBIToCollectionIfMissing(detailLineBICollection, detailLineBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetailLineBI to an array that doesn't contain it", () => {
        const detailLineBI: IDetailLineBI = sampleWithRequiredData;
        const detailLineBICollection: IDetailLineBI[] = [sampleWithPartialData];
        expectedResult = service.addDetailLineBIToCollectionIfMissing(detailLineBICollection, detailLineBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailLineBI);
      });

      it('should add only unique DetailLineBI to an array', () => {
        const detailLineBIArray: IDetailLineBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const detailLineBICollection: IDetailLineBI[] = [sampleWithRequiredData];
        expectedResult = service.addDetailLineBIToCollectionIfMissing(detailLineBICollection, ...detailLineBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detailLineBI: IDetailLineBI = sampleWithRequiredData;
        const detailLineBI2: IDetailLineBI = sampleWithPartialData;
        expectedResult = service.addDetailLineBIToCollectionIfMissing([], detailLineBI, detailLineBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailLineBI);
        expect(expectedResult).toContain(detailLineBI2);
      });

      it('should accept null and undefined values', () => {
        const detailLineBI: IDetailLineBI = sampleWithRequiredData;
        expectedResult = service.addDetailLineBIToCollectionIfMissing([], null, detailLineBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailLineBI);
      });

      it('should return initial array if no DetailLineBI is added', () => {
        const detailLineBICollection: IDetailLineBI[] = [sampleWithRequiredData];
        expectedResult = service.addDetailLineBIToCollectionIfMissing(detailLineBICollection, undefined, null);
        expect(expectedResult).toEqual(detailLineBICollection);
      });
    });

    describe('compareDetailLineBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDetailLineBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 8196 };
        const entity2 = null;

        const compareResult1 = service.compareDetailLineBI(entity1, entity2);
        const compareResult2 = service.compareDetailLineBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 8196 };
        const entity2 = { id: 15187 };

        const compareResult1 = service.compareDetailLineBI(entity1, entity2);
        const compareResult2 = service.compareDetailLineBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 8196 };
        const entity2 = { id: 8196 };

        const compareResult1 = service.compareDetailLineBI(entity1, entity2);
        const compareResult2 = service.compareDetailLineBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
