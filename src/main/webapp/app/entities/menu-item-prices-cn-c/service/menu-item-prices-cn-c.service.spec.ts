import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../menu-item-prices-cn-c.test-samples';

import { MenuItemPricesCnCService } from './menu-item-prices-cn-c.service';

const requireRestSample: IMenuItemPricesCnC = {
  ...sampleWithRequiredData,
};

describe('MenuItemPricesCnC Service', () => {
  let service: MenuItemPricesCnCService;
  let httpMock: HttpTestingController;
  let expectedResult: IMenuItemPricesCnC | IMenuItemPricesCnC[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MenuItemPricesCnCService);
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

    it('should create a MenuItemPricesCnC', () => {
      const menuItemPricesCnC = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(menuItemPricesCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MenuItemPricesCnC', () => {
      const menuItemPricesCnC = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(menuItemPricesCnC).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MenuItemPricesCnC', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MenuItemPricesCnC', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MenuItemPricesCnC', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMenuItemPricesCnCToCollectionIfMissing', () => {
      it('should add a MenuItemPricesCnC to an empty array', () => {
        const menuItemPricesCnC: IMenuItemPricesCnC = sampleWithRequiredData;
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing([], menuItemPricesCnC);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuItemPricesCnC);
      });

      it('should not add a MenuItemPricesCnC to an array that contains it', () => {
        const menuItemPricesCnC: IMenuItemPricesCnC = sampleWithRequiredData;
        const menuItemPricesCnCCollection: IMenuItemPricesCnC[] = [
          {
            ...menuItemPricesCnC,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing(menuItemPricesCnCCollection, menuItemPricesCnC);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MenuItemPricesCnC to an array that doesn't contain it", () => {
        const menuItemPricesCnC: IMenuItemPricesCnC = sampleWithRequiredData;
        const menuItemPricesCnCCollection: IMenuItemPricesCnC[] = [sampleWithPartialData];
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing(menuItemPricesCnCCollection, menuItemPricesCnC);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuItemPricesCnC);
      });

      it('should add only unique MenuItemPricesCnC to an array', () => {
        const menuItemPricesCnCArray: IMenuItemPricesCnC[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const menuItemPricesCnCCollection: IMenuItemPricesCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing(menuItemPricesCnCCollection, ...menuItemPricesCnCArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const menuItemPricesCnC: IMenuItemPricesCnC = sampleWithRequiredData;
        const menuItemPricesCnC2: IMenuItemPricesCnC = sampleWithPartialData;
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing([], menuItemPricesCnC, menuItemPricesCnC2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuItemPricesCnC);
        expect(expectedResult).toContain(menuItemPricesCnC2);
      });

      it('should accept null and undefined values', () => {
        const menuItemPricesCnC: IMenuItemPricesCnC = sampleWithRequiredData;
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing([], null, menuItemPricesCnC, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuItemPricesCnC);
      });

      it('should return initial array if no MenuItemPricesCnC is added', () => {
        const menuItemPricesCnCCollection: IMenuItemPricesCnC[] = [sampleWithRequiredData];
        expectedResult = service.addMenuItemPricesCnCToCollectionIfMissing(menuItemPricesCnCCollection, undefined, null);
        expect(expectedResult).toEqual(menuItemPricesCnCCollection);
      });
    });

    describe('compareMenuItemPricesCnC', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMenuItemPricesCnC(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 11591 };
        const entity2 = null;

        const compareResult1 = service.compareMenuItemPricesCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemPricesCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 11591 };
        const entity2 = { id: 29361 };

        const compareResult1 = service.compareMenuItemPricesCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemPricesCnC(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 11591 };
        const entity2 = { id: 11591 };

        const compareResult1 = service.compareMenuItemPricesCnC(entity1, entity2);
        const compareResult2 = service.compareMenuItemPricesCnC(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
