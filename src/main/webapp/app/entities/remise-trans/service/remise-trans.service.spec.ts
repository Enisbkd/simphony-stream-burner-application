import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRemiseTrans } from '../remise-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../remise-trans.test-samples';

import { RemiseTransService } from './remise-trans.service';

const requireRestSample: IRemiseTrans = {
  ...sampleWithRequiredData,
};

describe('RemiseTrans Service', () => {
  let service: RemiseTransService;
  let httpMock: HttpTestingController;
  let expectedResult: IRemiseTrans | IRemiseTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RemiseTransService);
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

    it('should create a RemiseTrans', () => {
      const remiseTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(remiseTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RemiseTrans', () => {
      const remiseTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(remiseTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RemiseTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RemiseTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RemiseTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRemiseTransToCollectionIfMissing', () => {
      it('should add a RemiseTrans to an empty array', () => {
        const remiseTrans: IRemiseTrans = sampleWithRequiredData;
        expectedResult = service.addRemiseTransToCollectionIfMissing([], remiseTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remiseTrans);
      });

      it('should not add a RemiseTrans to an array that contains it', () => {
        const remiseTrans: IRemiseTrans = sampleWithRequiredData;
        const remiseTransCollection: IRemiseTrans[] = [
          {
            ...remiseTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRemiseTransToCollectionIfMissing(remiseTransCollection, remiseTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RemiseTrans to an array that doesn't contain it", () => {
        const remiseTrans: IRemiseTrans = sampleWithRequiredData;
        const remiseTransCollection: IRemiseTrans[] = [sampleWithPartialData];
        expectedResult = service.addRemiseTransToCollectionIfMissing(remiseTransCollection, remiseTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remiseTrans);
      });

      it('should add only unique RemiseTrans to an array', () => {
        const remiseTransArray: IRemiseTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const remiseTransCollection: IRemiseTrans[] = [sampleWithRequiredData];
        expectedResult = service.addRemiseTransToCollectionIfMissing(remiseTransCollection, ...remiseTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const remiseTrans: IRemiseTrans = sampleWithRequiredData;
        const remiseTrans2: IRemiseTrans = sampleWithPartialData;
        expectedResult = service.addRemiseTransToCollectionIfMissing([], remiseTrans, remiseTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remiseTrans);
        expect(expectedResult).toContain(remiseTrans2);
      });

      it('should accept null and undefined values', () => {
        const remiseTrans: IRemiseTrans = sampleWithRequiredData;
        expectedResult = service.addRemiseTransToCollectionIfMissing([], null, remiseTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remiseTrans);
      });

      it('should return initial array if no RemiseTrans is added', () => {
        const remiseTransCollection: IRemiseTrans[] = [sampleWithRequiredData];
        expectedResult = service.addRemiseTransToCollectionIfMissing(remiseTransCollection, undefined, null);
        expect(expectedResult).toEqual(remiseTransCollection);
      });
    });

    describe('compareRemiseTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRemiseTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 19837 };
        const entity2 = null;

        const compareResult1 = service.compareRemiseTrans(entity1, entity2);
        const compareResult2 = service.compareRemiseTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 19837 };
        const entity2 = { id: 28204 };

        const compareResult1 = service.compareRemiseTrans(entity1, entity2);
        const compareResult2 = service.compareRemiseTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 19837 };
        const entity2 = { id: 19837 };

        const compareResult1 = service.compareRemiseTrans(entity1, entity2);
        const compareResult2 = service.compareRemiseTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
