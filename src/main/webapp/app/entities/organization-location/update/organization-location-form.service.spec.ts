import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../organization-location.test-samples';

import { OrganizationLocationFormService } from './organization-location-form.service';

describe('OrganizationLocation Form Service', () => {
  let service: OrganizationLocationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationLocationFormService);
  });

  describe('Service methods', () => {
    describe('createOrganizationLocationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganizationLocationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            name: expect.any(Object),
            currency: expect.any(Object),
            phoneNumber: expect.any(Object),
            languages: expect.any(Object),
            timezoneIanaName: expect.any(Object),
            timezoneWindowsName: expect.any(Object),
            timezoneTzIndex: expect.any(Object),
            addressLine1: expect.any(Object),
            addressLine2: expect.any(Object),
            addressFloor: expect.any(Object),
            addressLocality: expect.any(Object),
            addressRegion: expect.any(Object),
            addressPostalCode: expect.any(Object),
            addressCountry: expect.any(Object),
            addressNotes: expect.any(Object),
            geoLatitude: expect.any(Object),
            geoLongitude: expect.any(Object),
            posPlatformName: expect.any(Object),
            posPlatformVersion: expect.any(Object),
          }),
        );
      });

      it('passing IOrganizationLocation should create a new form with FormGroup', () => {
        const formGroup = service.createOrganizationLocationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            name: expect.any(Object),
            currency: expect.any(Object),
            phoneNumber: expect.any(Object),
            languages: expect.any(Object),
            timezoneIanaName: expect.any(Object),
            timezoneWindowsName: expect.any(Object),
            timezoneTzIndex: expect.any(Object),
            addressLine1: expect.any(Object),
            addressLine2: expect.any(Object),
            addressFloor: expect.any(Object),
            addressLocality: expect.any(Object),
            addressRegion: expect.any(Object),
            addressPostalCode: expect.any(Object),
            addressCountry: expect.any(Object),
            addressNotes: expect.any(Object),
            geoLatitude: expect.any(Object),
            geoLongitude: expect.any(Object),
            posPlatformName: expect.any(Object),
            posPlatformVersion: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrganizationLocation', () => {
      it('should return NewOrganizationLocation for default OrganizationLocation initial value', () => {
        const formGroup = service.createOrganizationLocationFormGroup(sampleWithNewData);

        const organizationLocation = service.getOrganizationLocation(formGroup) as any;

        expect(organizationLocation).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganizationLocation for empty OrganizationLocation initial value', () => {
        const formGroup = service.createOrganizationLocationFormGroup();

        const organizationLocation = service.getOrganizationLocation(formGroup) as any;

        expect(organizationLocation).toMatchObject({});
      });

      it('should return IOrganizationLocation', () => {
        const formGroup = service.createOrganizationLocationFormGroup(sampleWithRequiredData);

        const organizationLocation = service.getOrganizationLocation(formGroup) as any;

        expect(organizationLocation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganizationLocation should not enable id FormControl', () => {
        const formGroup = service.createOrganizationLocationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganizationLocation should disable id FormControl', () => {
        const formGroup = service.createOrganizationLocationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
