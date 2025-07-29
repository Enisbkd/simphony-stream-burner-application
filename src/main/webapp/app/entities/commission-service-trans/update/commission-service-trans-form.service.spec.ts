import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../commission-service-trans.test-samples';

import { CommissionServiceTransFormService } from './commission-service-trans-form.service';

describe('CommissionServiceTrans Form Service', () => {
  let service: CommissionServiceTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommissionServiceTransFormService);
  });

  describe('Service methods', () => {
    describe('createCommissionServiceTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommissionServiceTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            serviceChargeId: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            value: expect.any(Object),
          }),
        );
      });

      it('passing ICommissionServiceTrans should create a new form with FormGroup', () => {
        const formGroup = service.createCommissionServiceTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            serviceChargeId: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            value: expect.any(Object),
          }),
        );
      });
    });

    describe('getCommissionServiceTrans', () => {
      it('should return NewCommissionServiceTrans for default CommissionServiceTrans initial value', () => {
        const formGroup = service.createCommissionServiceTransFormGroup(sampleWithNewData);

        const commissionServiceTrans = service.getCommissionServiceTrans(formGroup) as any;

        expect(commissionServiceTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommissionServiceTrans for empty CommissionServiceTrans initial value', () => {
        const formGroup = service.createCommissionServiceTransFormGroup();

        const commissionServiceTrans = service.getCommissionServiceTrans(formGroup) as any;

        expect(commissionServiceTrans).toMatchObject({});
      });

      it('should return ICommissionServiceTrans', () => {
        const formGroup = service.createCommissionServiceTransFormGroup(sampleWithRequiredData);

        const commissionServiceTrans = service.getCommissionServiceTrans(formGroup) as any;

        expect(commissionServiceTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommissionServiceTrans should not enable id FormControl', () => {
        const formGroup = service.createCommissionServiceTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommissionServiceTrans should disable id FormControl', () => {
        const formGroup = service.createCommissionServiceTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
