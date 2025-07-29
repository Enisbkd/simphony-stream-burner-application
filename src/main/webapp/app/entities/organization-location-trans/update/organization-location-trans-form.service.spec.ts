import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../organization-location-trans.test-samples';

import { OrganizationLocationTransFormService } from './organization-location-trans-form.service';

describe('OrganizationLocationTrans Form Service', () => {
  let service: OrganizationLocationTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizationLocationTransFormService);
  });

  describe('Service methods', () => {
    describe('createOrganizationLocationTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup();

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

      it('passing IOrganizationLocationTrans should create a new form with FormGroup', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup(sampleWithRequiredData);

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

    describe('getOrganizationLocationTrans', () => {
      it('should return NewOrganizationLocationTrans for default OrganizationLocationTrans initial value', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup(sampleWithNewData);

        const organizationLocationTrans = service.getOrganizationLocationTrans(formGroup) as any;

        expect(organizationLocationTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganizationLocationTrans for empty OrganizationLocationTrans initial value', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup();

        const organizationLocationTrans = service.getOrganizationLocationTrans(formGroup) as any;

        expect(organizationLocationTrans).toMatchObject({});
      });

      it('should return IOrganizationLocationTrans', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup(sampleWithRequiredData);

        const organizationLocationTrans = service.getOrganizationLocationTrans(formGroup) as any;

        expect(organizationLocationTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganizationLocationTrans should not enable id FormControl', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganizationLocationTrans should disable id FormControl', () => {
        const formGroup = service.createOrganizationLocationTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
