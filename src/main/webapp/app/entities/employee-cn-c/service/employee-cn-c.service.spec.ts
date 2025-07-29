import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmployeeCnC } from '../employee-cn-c.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../employee-cn-c.test-samples';

import { EmployeeCnCService } from './employee-cn-c.service';

const requireRestSample: IEmployeeCnC = {
  ...sampleWithRequiredData,
};

describe('EmployeeCnC Service', () => {
  let service: EmployeeCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmployeeCnC | IEmployeeCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmployeeCnCService);
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

    it('should create a EmployeeCnC', () => {
      const employeeCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(employeeCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmployeeCnC', () => {
      const employeeCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(employeeCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmployeeCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmployeeCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmployeeCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmployeeCnCToCollectionIfMissing', () => {
      it('should add a EmployeeCnC to an empty array', () => {
        const employeeCnC: IEmployeeCnC = sampleWithRequiredData;
        expectedResult = service.addEmployeeCnCToCollectionIfMissing([], employeeCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employeeCnC);
      });

      it('should not add a EmployeeCnC to an array that contains it', () => {
        const employeeCnC: IEmployeeCnC = sampleWithRequiredData;
        const employeeCnCCollection: IEmployeeCnC[] = [
          {
            ...employeeCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmployeeCnCToCollectionIfMissing(employeeCnCCollection, employeeCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmployeeCnC to an array that doesn't contain it", () => {
        const employeeCnC: IEmployeeCnC = sampleWithRequiredData;
        const employeeCnCCollection: IEmployeeCnC[] = [sampleWithPartialData];
        expectedResult = service.addEmployeeCnCToCollectionIfMissing(employeeCnCCollection, employeeCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employeeCnC);
      });

      it('should add only unique EmployeeCnC to an array', () => {
        const employeeCnCArray: IEmployeeCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const employeeCnCCollection: IEmployeeCnC[] = [sampleWithRequiredData];
        expectedResult = service.addEmployeeCnCToCollectionIfMissing(employeeCnCCollection, ...employeeCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const employeeCnC: IEmployeeCnC = sampleWithRequiredData;
        const employeeCnC2: IEmployeeCnC = sampleWithPartialData;
        expectedResult = service.addEmployeeCnCToCollectionIfMissing([], employeeCnC, employeeCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employeeCnC);
        expect(expectedResult).toContain(employeeCnC2);
      });

      it('should accept null and undefined values', () => {
        const employeeCnC: IEmployeeCnC = sampleWithRequiredData;
        expectedResult = service.addEmployeeCnCToCollectionIfMissing([], null, employeeCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employeeCnC);
      });

      it('should return initial array if no EmployeeCnC is added', () => {
        const employeeCnCCollection: IEmployeeCnC[] = [sampleWithRequiredData];
        expectedResult = service.addEmployeeCnCToCollectionIfMissing(employeeCnCCollection, undefined, null);
        expect(expectedResult).toEqual(employeeCnCCollection);
      });
    });

    describe('compareEmployeeCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmployeeCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 8307 };
        const entity2 = null;

        const compareResult1 = service.compareEmployeeCnC(entity1, entity2);
        const compareResult2 = service.compareEmployeeCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 8307 };
        const entity2 = { id: 11194 };

        const compareResult1 = service.compareEmployeeCnC(entity1, entity2);
        const compareResult2 = service.compareEmployeeCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 8307 };
        const entity2 = { id: 8307 };

        const compareResult1 = service.compareEmployeeCnC(entity1, entity2);
        const compareResult2 = service.compareEmployeeCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
