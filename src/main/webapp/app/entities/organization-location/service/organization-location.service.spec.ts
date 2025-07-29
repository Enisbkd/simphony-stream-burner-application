import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOrganizationLocation } from '../organization-location.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../organization-location.test-samples';

import { OrganizationLocationService } from './organization-location.service';

const requireRestSample: IOrganizationLocation = {
  ...sampleWithRequiredData,
};

describe('OrganizationLocation Service', () => {
  let service: OrganizationLocationService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganizationLocation | IOrganizationLocation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationLocationService);
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

    it('should create a OrganizationLocation', () => {
      const organizationLocation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organizationLocation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationLocation', () => {
      const organizationLocation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organizationLocation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationLocation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationLocation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrganizationLocation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganizationLocationToCollectionIfMissing', () => {
      it('should add a OrganizationLocation to an empty array', () => {
        const organizationLocation: IOrganizationLocation = sampleWithRequiredData;
        expectedResult = service.addOrganizationLocationToCollectionIfMissing([], organizationLocation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationLocation);
      });

      it('should not add a OrganizationLocation to an array that contains it', () => {
        const organizationLocation: IOrganizationLocation = sampleWithRequiredData;
        const organizationLocationCollection: IOrganizationLocation[] = [
          {
            ...organizationLocation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganizationLocationToCollectionIfMissing(organizationLocationCollection, organizationLocation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationLocation to an array that doesn't contain it", () => {
        const organizationLocation: IOrganizationLocation = sampleWithRequiredData;
        const organizationLocationCollection: IOrganizationLocation[] = [sampleWithPartialData];
        expectedResult = service.addOrganizationLocationToCollectionIfMissing(organizationLocationCollection, organizationLocation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationLocation);
      });

      it('should add only unique OrganizationLocation to an array', () => {
        const organizationLocationArray: IOrganizationLocation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const organizationLocationCollection: IOrganizationLocation[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationLocationToCollectionIfMissing(organizationLocationCollection, ...organizationLocationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationLocation: IOrganizationLocation = sampleWithRequiredData;
        const organizationLocation2: IOrganizationLocation = sampleWithPartialData;
        expectedResult = service.addOrganizationLocationToCollectionIfMissing([], organizationLocation, organizationLocation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationLocation);
        expect(expectedResult).toContain(organizationLocation2);
      });

      it('should accept null and undefined values', () => {
        const organizationLocation: IOrganizationLocation = sampleWithRequiredData;
        expectedResult = service.addOrganizationLocationToCollectionIfMissing([], null, organizationLocation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationLocation);
      });

      it('should return initial array if no OrganizationLocation is added', () => {
        const organizationLocationCollection: IOrganizationLocation[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizationLocationToCollectionIfMissing(organizationLocationCollection, undefined, null);
        expect(expectedResult).toEqual(organizationLocationCollection);
      });
    });

    describe('compareOrganizationLocation', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganizationLocation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18333 };
        const entity2 = null;

        const compareResult1 = service.compareOrganizationLocation(entity1, entity2);
        const compareResult2 = service.compareOrganizationLocation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18333 };
        const entity2 = { id: 12860 };

        const compareResult1 = service.compareOrganizationLocation(entity1, entity2);
        const compareResult2 = service.compareOrganizationLocation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18333 };
        const entity2 = { id: 18333 };

        const compareResult1 = service.compareOrganizationLocation(entity1, entity2);
        const compareResult2 = service.compareOrganizationLocation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
