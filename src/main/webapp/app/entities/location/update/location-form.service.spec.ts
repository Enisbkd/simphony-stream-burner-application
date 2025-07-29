import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../location.test-samples';

import { LocationFormService } from './location-form.service';

describe('Location Form Service', () => {
  let service: LocationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocationFormService);
  });

  describe('Service methods', () => {
    describe('createLocationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLocationFormGroup();

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

      it('passing ILocation should create a new form with FormGroup', () => {
        const formGroup = service.createLocationFormGroup(sampleWithRequiredData);

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

    describe('getLocation', () => {
      it('should return NewLocation for default Location initial value', () => {
        const formGroup = service.createLocationFormGroup(sampleWithNewData);

        const location = service.getLocation(formGroup) as any;

        expect(location).toMatchObject(sampleWithNewData);
      });

      it('should return NewLocation for empty Location initial value', () => {
        const formGroup = service.createLocationFormGroup();

        const location = service.getLocation(formGroup) as any;

        expect(location).toMatchObject({});
      });

      it('should return ILocation', () => {
        const formGroup = service.createLocationFormGroup(sampleWithRequiredData);

        const location = service.getLocation(formGroup) as any;

        expect(location).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILocation should not enable id FormControl', () => {
        const formGroup = service.createLocationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLocation should disable id FormControl', () => {
        const formGroup = service.createLocationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
