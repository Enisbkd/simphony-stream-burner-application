import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IModePaiementTrans } from '../mode-paiement-trans.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../mode-paiement-trans.test-samples';

import { ModePaiementTransService } from './mode-paiement-trans.service';

const requireRestSample: IModePaiementTrans = {
  ...sampleWithRequiredData,
};

describe('ModePaiementTrans Service', () => {
  let service: ModePaiementTransService;
  let httpMock: HttpTestingController;
  let expectedResult: IModePaiementTrans | IModePaiementTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ModePaiementTransService);
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

    it('should create a ModePaiementTrans', () => {
      const modePaiementTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(modePaiementTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ModePaiementTrans', () => {
      const modePaiementTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(modePaiementTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ModePaiementTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ModePaiementTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ModePaiementTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addModePaiementTransToCollectionIfMissing', () => {
      it('should add a ModePaiementTrans to an empty array', () => {
        const modePaiementTrans: IModePaiementTrans = sampleWithRequiredData;
        expectedResult = service.addModePaiementTransToCollectionIfMissing([], modePaiementTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modePaiementTrans);
      });

      it('should not add a ModePaiementTrans to an array that contains it', () => {
        const modePaiementTrans: IModePaiementTrans = sampleWithRequiredData;
        const modePaiementTransCollection: IModePaiementTrans[] = [
          {
            ...modePaiementTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addModePaiementTransToCollectionIfMissing(modePaiementTransCollection, modePaiementTrans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ModePaiementTrans to an array that doesn't contain it", () => {
        const modePaiementTrans: IModePaiementTrans = sampleWithRequiredData;
        const modePaiementTransCollection: IModePaiementTrans[] = [sampleWithPartialData];
        expectedResult = service.addModePaiementTransToCollectionIfMissing(modePaiementTransCollection, modePaiementTrans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modePaiementTrans);
      });

      it('should add only unique ModePaiementTrans to an array', () => {
        const modePaiementTransArray: IModePaiementTrans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const modePaiementTransCollection: IModePaiementTrans[] = [sampleWithRequiredData];
        expectedResult = service.addModePaiementTransToCollectionIfMissing(modePaiementTransCollection, ...modePaiementTransArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modePaiementTrans: IModePaiementTrans = sampleWithRequiredData;
        const modePaiementTrans2: IModePaiementTrans = sampleWithPartialData;
        expectedResult = service.addModePaiementTransToCollectionIfMissing([], modePaiementTrans, modePaiementTrans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modePaiementTrans);
        expect(expectedResult).toContain(modePaiementTrans2);
      });

      it('should accept null and undefined values', () => {
        const modePaiementTrans: IModePaiementTrans = sampleWithRequiredData;
        expectedResult = service.addModePaiementTransToCollectionIfMissing([], null, modePaiementTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modePaiementTrans);
      });

      it('should return initial array if no ModePaiementTrans is added', () => {
        const modePaiementTransCollection: IModePaiementTrans[] = [sampleWithRequiredData];
        expectedResult = service.addModePaiementTransToCollectionIfMissing(modePaiementTransCollection, undefined, null);
        expect(expectedResult).toEqual(modePaiementTransCollection);
      });
    });

    describe('compareModePaiementTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareModePaiementTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 15721 };
        const entity2 = null;

        const compareResult1 = service.compareModePaiementTrans(entity1, entity2);
        const compareResult2 = service.compareModePaiementTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 15721 };
        const entity2 = { id: 26549 };

        const compareResult1 = service.compareModePaiementTrans(entity1, entity2);
        const compareResult2 = service.compareModePaiementTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 15721 };
        const entity2 = { id: 15721 };

        const compareResult1 = service.compareModePaiementTrans(entity1, entity2);
        const compareResult2 = service.compareModePaiementTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
