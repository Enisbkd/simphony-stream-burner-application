import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../location-cn-c.test-samples';

import { LocationCnCFormService } from './location-cn-c-form.service';

describe('LocationCnC Form Service', () => {
  let service: LocationCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocationCnCFormService);
  });

  describe('Service methods', () => {
    describe('createLocationCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLocationCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            tzIndex: expect.any(Object),
            tzName: expect.any(Object),
            localeInfoId: expect.any(Object),
            name: expect.any(Object),
            reportingLocName: expect.any(Object),
            locRef: expect.any(Object),
            reportingParentEnterpriseLevelName: expect.any(Object),
            objectNum: expect.any(Object),
            sbmPmsIfcIp: expect.any(Object),
            sbmPmsIfcPort: expect.any(Object),
            sbmPriveRoomStart: expect.any(Object),
            sbmPriveRoomEnd: expect.any(Object),
            sbmPmsSendAllDetails: expect.any(Object),
            sbmPmsSendFullDscv: expect.any(Object),
            sbmPmsSend64Tax: expect.any(Object),
            sbmCardPaymentUrl: expect.any(Object),
            sbmCheckHotelDataUrl: expect.any(Object),
            sbmVoucherSvcUrl: expect.any(Object),
            sbmVoucherInvPm: expect.any(Object),
            sbmVoucherCorpPm: expect.any(Object),
            sbmVoucherRewardPm: expect.any(Object),
            sbmVoucherMcPm: expect.any(Object),
            sbmPmsIfcPort2: expect.any(Object),
            sbmPmsIfcPort3: expect.any(Object),
            sbmPmsIfcPort4: expect.any(Object),
            sbmTimeout: expect.any(Object),
          }),
        );
      });

      it('passing ILocationCnC should create a new form with FormGroup', () => {
        const formGroup = service.createLocationCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            tzIndex: expect.any(Object),
            tzName: expect.any(Object),
            localeInfoId: expect.any(Object),
            name: expect.any(Object),
            reportingLocName: expect.any(Object),
            locRef: expect.any(Object),
            reportingParentEnterpriseLevelName: expect.any(Object),
            objectNum: expect.any(Object),
            sbmPmsIfcIp: expect.any(Object),
            sbmPmsIfcPort: expect.any(Object),
            sbmPriveRoomStart: expect.any(Object),
            sbmPriveRoomEnd: expect.any(Object),
            sbmPmsSendAllDetails: expect.any(Object),
            sbmPmsSendFullDscv: expect.any(Object),
            sbmPmsSend64Tax: expect.any(Object),
            sbmCardPaymentUrl: expect.any(Object),
            sbmCheckHotelDataUrl: expect.any(Object),
            sbmVoucherSvcUrl: expect.any(Object),
            sbmVoucherInvPm: expect.any(Object),
            sbmVoucherCorpPm: expect.any(Object),
            sbmVoucherRewardPm: expect.any(Object),
            sbmVoucherMcPm: expect.any(Object),
            sbmPmsIfcPort2: expect.any(Object),
            sbmPmsIfcPort3: expect.any(Object),
            sbmPmsIfcPort4: expect.any(Object),
            sbmTimeout: expect.any(Object),
          }),
        );
      });
    });

    describe('getLocationCnC', () => {
      it('should return NewLocationCnC for default LocationCnC initial value', () => {
        const formGroup = service.createLocationCnCFormGroup(sampleWithNewData);

        const locationCnC = service.getLocationCnC(formGroup) as any;

        expect(locationCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewLocationCnC for empty LocationCnC initial value', () => {
        const formGroup = service.createLocationCnCFormGroup();

        const locationCnC = service.getLocationCnC(formGroup) as any;

        expect(locationCnC).toMatchObject({});
      });

      it('should return ILocationCnC', () => {
        const formGroup = service.createLocationCnCFormGroup(sampleWithRequiredData);

        const locationCnC = service.getLocationCnC(formGroup) as any;

        expect(locationCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILocationCnC should not enable id FormControl', () => {
        const formGroup = service.createLocationCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLocationCnC should disable id FormControl', () => {
        const formGroup = service.createLocationCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
