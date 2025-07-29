import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../menu-item-masters-cn-c.test-samples';

import { MenuItemMastersCnCService } from './menu-item-masters-cn-c.service';

const requireRestSample: IMenuItemMastersCnC = {
  ...sampleWithRequiredData,
};

describe('MenuItemMastersCnC Service', () => {
  let service: MenuItemMastersCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IMenuItemMastersCnC | IMenuItemMastersCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MenuItemMastersCnCService);
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

    it('should create a MenuItemMastersCnC', () => {
      const menuItemMastersCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(menuItemMastersCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MenuItemMastersCnC', () => {
      const menuItemMastersCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(menuItemMastersCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MenuItemMastersCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MenuItemMastersCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MenuItemMastersCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMenuItemMastersCnCToCollectionIfMissing', () => {
      it('should add a MenuItemMastersCnC to an empty array', () => {
        const menuItemMastersCnC: IMenuItemMastersCnC = sampleWithRequiredData;
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing([], menuItemMastersCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuItemMastersCnC);
      });

      it('should not add a MenuItemMastersCnC to an array that contains it', () => {
        const menuItemMastersCnC: IMenuItemMastersCnC = sampleWithRequiredData;
        const menuItemMastersCnCCollection: IMenuItemMastersCnC[] = [
          {
            ...menuItemMastersCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing(menuItemMastersCnCCollection, menuItemMastersCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MenuItemMastersCnC to an array that doesn't contain it", () => {
        const menuItemMastersCnC: IMenuItemMastersCnC = sampleWithRequiredData;
        const menuItemMastersCnCCollection: IMenuItemMastersCnC[] = [sampleWithPartialData];
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing(menuItemMastersCnCCollection, menuItemMastersCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuItemMastersCnC);
      });

      it('should add only unique MenuItemMastersCnC to an array', () => {
        const menuItemMastersCnCArray: IMenuItemMastersCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const menuItemMastersCnCCollection: IMenuItemMastersCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing(menuItemMastersCnCCollection, ...menuItemMastersCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const menuItemMastersCnC: IMenuItemMastersCnC = sampleWithRequiredData;
        const menuItemMastersCnC2: IMenuItemMastersCnC = sampleWithPartialData;
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing([], menuItemMastersCnC, menuItemMastersCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuItemMastersCnC);
        expect(expectedResult).toContain(menuItemMastersCnC2);
      });

      it('should accept null and undefined values', () => {
        const menuItemMastersCnC: IMenuItemMastersCnC = sampleWithRequiredData;
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing([], null, menuItemMastersCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuItemMastersCnC);
      });

      it('should return initial array if no MenuItemMastersCnC is added', () => {
        const menuItemMastersCnCCollection: IMenuItemMastersCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMenuItemMastersCnCToCollectionIfMissing(menuItemMastersCnCCollection, undefined, null);
        expect(expectedResult).toEqual(menuItemMastersCnCCollection);
      });
    });

    describe('compareMenuItemMastersCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMenuItemMastersCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 27918 };
        const entity2 = null;

        const compareResult1 = service.compareMenuItemMastersCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemMastersCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 27918 };
        const entity2 = { id: 30734 };

        const compareResult1 = service.compareMenuItemMastersCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemMastersCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 27918 };
        const entity2 = { id: 27918 };

        const compareResult1 = service.compareMenuItemMastersCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemMastersCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
