import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRemiseBI } from '../remise-bi.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../remise-bi.test-samples';

import { RemiseBIService } from './remise-bi.service';

const requireRestSample: IRemiseBI = {
  ...sampleWithRequiredData,
};

describe('RemiseBI Service', () => {
  let service: RemiseBIService;
  let httpMock: HttpTestingController;
  let expectedResult: IRemiseBI | IRemiseBI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RemiseBIService);
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

    it('should create a RemiseBI', () => {
      const remiseBI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(remiseBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RemiseBI', () => {
      const remiseBI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(remiseBI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RemiseBI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RemiseBI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RemiseBI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRemiseBIToCollectionIfMissing', () => {
      it('should add a RemiseBI to an empty array', () => {
        const remiseBI: IRemiseBI = sampleWithRequiredData;
        expectedResult = service.addRemiseBIToCollectionIfMissing([], remiseBI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remiseBI);
      });

      it('should not add a RemiseBI to an array that contains it', () => {
        const remiseBI: IRemiseBI = sampleWithRequiredData;
        const remiseBICollection: IRemiseBI[] = [
          {
            ...remiseBI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRemiseBIToCollectionIfMissing(remiseBICollection, remiseBI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RemiseBI to an array that doesn't contain it", () => {
        const remiseBI: IRemiseBI = sampleWithRequiredData;
        const remiseBICollection: IRemiseBI[] = [sampleWithPartialData];
        expectedResult = service.addRemiseBIToCollectionIfMissing(remiseBICollection, remiseBI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remiseBI);
      });

      it('should add only unique RemiseBI to an array', () => {
        const remiseBIArray: IRemiseBI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const remiseBICollection: IRemiseBI[] = [sampleWithRequiredData];
        expectedResult = service.addRemiseBIToCollectionIfMissing(remiseBICollection, ...remiseBIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const remiseBI: IRemiseBI = sampleWithRequiredData;
        const remiseBI2: IRemiseBI = sampleWithPartialData;
        expectedResult = service.addRemiseBIToCollectionIfMissing([], remiseBI, remiseBI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remiseBI);
        expect(expectedResult).toContain(remiseBI2);
      });

      it('should accept null and undefined values', () => {
        const remiseBI: IRemiseBI = sampleWithRequiredData;
        expectedResult = service.addRemiseBIToCollectionIfMissing([], null, remiseBI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remiseBI);
      });

      it('should return initial array if no RemiseBI is added', () => {
        const remiseBICollection: IRemiseBI[] = [sampleWithRequiredData];
        expectedResult = service.addRemiseBIToCollectionIfMissing(remiseBICollection, undefined, null);
        expect(expectedResult).toEqual(remiseBICollection);
      });
    });

    describe('compareRemiseBI', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRemiseBI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 31748 };
        const entity2 = null;

        const compareResult1 = service.compareRemiseBI(entity1, entity2);
        const compareResult2 = service.compareRemiseBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 31748 };
        const entity2 = { id: 21786 };

        const compareResult1 = service.compareRemiseBI(entity1, entity2);
        const compareResult2 = service.compareRemiseBI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 31748 };
        const entity2 = { id: 31748 };

        const compareResult1 = service.compareRemiseBI(entity1, entity2);
        const compareResult2 = service.compareRemiseBI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
