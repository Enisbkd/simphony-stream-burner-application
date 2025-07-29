import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../menu-item-definitions-cn-c.test-samples';

import { MenuItemDefinitionsCnCService } from './menu-item-definitions-cn-c.service';

const requireRestSample: IMenuItemDefinitionsCnC = {
  ...sampleWithRequiredData,
};

describe('MenuItemDefinitionsCnC Service', () => {
  let service: MenuItemDefinitionsCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IMenuItemDefinitionsCnC | IMenuItemDefinitionsCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MenuItemDefinitionsCnCService);
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

    it('should create a MenuItemDefinitionsCnC', () => {
      const menuItemDefinitionsCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(menuItemDefinitionsCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MenuItemDefinitionsCnC', () => {
      const menuItemDefinitionsCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(menuItemDefinitionsCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MenuItemDefinitionsCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MenuItemDefinitionsCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MenuItemDefinitionsCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMenuItemDefinitionsCnCToCollectionIfMissing', () => {
      it('should add a MenuItemDefinitionsCnC to an empty array', () => {
        const menuItemDefinitionsCnC: IMenuItemDefinitionsCnC = sampleWithRequiredData;
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing([], menuItemDefinitionsCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuItemDefinitionsCnC);
      });

      it('should not add a MenuItemDefinitionsCnC to an array that contains it', () => {
        const menuItemDefinitionsCnC: IMenuItemDefinitionsCnC = sampleWithRequiredData;
        const menuItemDefinitionsCnCCollection: IMenuItemDefinitionsCnC[] = [
          {
            ...menuItemDefinitionsCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing(menuItemDefinitionsCnCCollection, menuItemDefinitionsCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MenuItemDefinitionsCnC to an array that doesn't contain it", () => {
        const menuItemDefinitionsCnC: IMenuItemDefinitionsCnC = sampleWithRequiredData;
        const menuItemDefinitionsCnCCollection: IMenuItemDefinitionsCnC[] = [sampleWithPartialData];
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing(menuItemDefinitionsCnCCollection, menuItemDefinitionsCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuItemDefinitionsCnC);
      });

      it('should add only unique MenuItemDefinitionsCnC to an array', () => {
        const menuItemDefinitionsCnCArray: IMenuItemDefinitionsCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const menuItemDefinitionsCnCCollection: IMenuItemDefinitionsCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing(
          menuItemDefinitionsCnCCollection,
          ...menuItemDefinitionsCnCArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const menuItemDefinitionsCnC: IMenuItemDefinitionsCnC = sampleWithRequiredData;
        const menuItemDefinitionsCnC2: IMenuItemDefinitionsCnC = sampleWithPartialData;
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing([], menuItemDefinitionsCnC, menuItemDefinitionsCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuItemDefinitionsCnC);
        expect(expectedResult).toContain(menuItemDefinitionsCnC2);
      });

      it('should accept null and undefined values', () => {
        const menuItemDefinitionsCnC: IMenuItemDefinitionsCnC = sampleWithRequiredData;
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing([], null, menuItemDefinitionsCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuItemDefinitionsCnC);
      });

      it('should return initial array if no MenuItemDefinitionsCnC is added', () => {
        const menuItemDefinitionsCnCCollection: IMenuItemDefinitionsCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMenuItemDefinitionsCnCToCollectionIfMissing(menuItemDefinitionsCnCCollection, undefined, null);
        expect(expectedResult).toEqual(menuItemDefinitionsCnCCollection);
      });
    });

    describe('compareMenuItemDefinitionsCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMenuItemDefinitionsCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 19209 };
        const entity2 = null;

        const compareResult1 = service.compareMenuItemDefinitionsCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemDefinitionsCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 19209 };
        const entity2 = { id: 23772 };

        const compareResult1 = service.compareMenuItemDefinitionsCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemDefinitionsCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 19209 };
        const entity2 = { id: 19209 };

        const compareResult1 = service.compareMenuItemDefinitionsCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemDefinitionsCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
