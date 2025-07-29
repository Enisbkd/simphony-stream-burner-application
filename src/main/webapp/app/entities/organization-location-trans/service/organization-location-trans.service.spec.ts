import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrganizationLocationTrans } from '../organization-location-trans.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../organization-location-trans.test-samples';

import { OrganizationLocationTransService } from './organization-location-trans.service';

const requireRestSample: IOrganizationLocationTrans = {
  ...sampleWithRequiredData,
};

describe('OrganizationLocationTrans Service', () => {
  let service: OrganizationLocationTransService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganizationLocationTrans | IOrganizationLocationTrans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationLocationTransService);
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

    it('should create a OrganizationLocationTrans', () => {
      const organizationLocationTrans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organizationLocationTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationLocationTrans', () => {
      const organizationLocationTrans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organizationLocationTrans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationLocationTrans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationLocationTrans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrganizationLocationTrans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganizationLocationTransToCollectionIfMissing', () => {
      it('should add a OrganizationLocationTrans to an empty array', () => {
        const organizationLocationTrans: IOrganizationLocationTrans = sampleWithRequiredData;
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing([], organizationLocationTrans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationLocationTrans);
      });

      it('should not add a OrganizationLocationTrans to an array that contains it', () => {
        const organizationLocationTrans: IOrganizationLocationTrans = sampleWithRequiredData;
        const organizationLocationTransCollection: IOrganizationLocationTrans[] = [
          {
            ...organizationLocationTrans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing(
          organizationLocationTransCollection,
          organizationLocationTrans,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationLocationTrans to an array that doesn't contain it", () => {
        const organizationLocationTrans: IOrganizationLocationTrans = sampleWithRequiredData;
        const organizationLocationTransCollection: IOrganizationLocationTrans[] = [sampleWithPartialData];
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing(
          organizationLocationTransCollection,
          organizationLocationTrans,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationLocationTrans);
      });

      it('should add only unique OrganizationLocationTrans to an array', () => {
        const organizationLocationTransArray: IOrganizationLocationTrans[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const organizationLocationTransCollection: IOrganizationLocationTrans[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing(
          organizationLocationTransCollection,
          ...organizationLocationTransArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationLocationTrans: IOrganizationLocationTrans = sampleWithRequiredData;
        const organizationLocationTrans2: IOrganizationLocationTrans = sampleWithPartialData;
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing(
          [],
          organizationLocationTrans,
          organizationLocationTrans2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationLocationTrans);
        expect(expectedResult).toContain(organizationLocationTrans2);
      });

      it('should accept null and undefined values', () => {
        const organizationLocationTrans: IOrganizationLocationTrans = sampleWithRequiredData;
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing([], null, organizationLocationTrans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationLocationTrans);
      });

      it('should return initial array if no OrganizationLocationTrans is added', () => {
        const organizationLocationTransCollection: IOrganizationLocationTrans[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationLocationTransToCollectionIfMissing(organizationLocationTransCollection, undefined, null);
        expect(expectedResult).toEqual(organizationLocationTransCollection);
      });
    });

    describe('compareOrganizationLocationTrans', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganizationLocationTrans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 30457 };
        const entity2 = null;

        const compareResult1 = service.compareOrganizationLocationTrans(entity1, entity2);
        const compareResult2 = service.compareOrganizationLocationTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 30457 };
        const entity2 = { id: 9475 };

        const compareResult1 = service.compareOrganizationLocationTrans(entity1, entity2);
        const compareResult2 = service.compareOrganizationLocationTrans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 30457 };
        const entity2 = { id: 30457 };

        const compareResult1 = service.compareOrganizationLocationTrans(entity1, entity2);
        const compareResult2 = service.compareOrganizationLocationTrans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
