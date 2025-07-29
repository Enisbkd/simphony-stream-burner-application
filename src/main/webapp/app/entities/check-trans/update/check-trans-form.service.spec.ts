import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../check-trans.test-samples';

import { CheckTransFormService } from './check-trans-form.service';

describe('CheckTrans Form Service', () => {
  let service: CheckTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckTransFormService);
  });

  describe('Service methods', () => {
    describe('createCheckTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCheckTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rvcRef: expect.any(Object),
            checkRef: expect.any(Object),
            checkNumber: expect.any(Object),
            checkName: expect.any(Object),
            checkEmployeeRef: expect.any(Object),
            orderTypeRef: expect.any(Object),
            orderChannelRef: expect.any(Object),
            tableName: expect.any(Object),
            tableGroupNumber: expect.any(Object),
            openTime: expect.any(Object),
            guestCount: expect.any(Object),
            language: expect.any(Object),
            isTrainingCheck: expect.any(Object),
            status: expect.any(Object),
            preparationStatus: expect.any(Object),
            subtotal: expect.any(Object),
            subtotalDiscountTotal: expect.any(Object),
            autoServiceChargeTotal: expect.any(Object),
            serviceChargeTotal: expect.any(Object),
            taxTotal: expect.any(Object),
            paymentTotal: expect.any(Object),
            totalDue: expect.any(Object),
            taxRateId: expect.any(Object),
          }),
        );
      });

      it('passing ICheckTrans should create a new form with FormGroup', () => {
        const formGroup = service.createCheckTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rvcRef: expect.any(Object),
            checkRef: expect.any(Object),
            checkNumber: expect.any(Object),
            checkName: expect.any(Object),
            checkEmployeeRef: expect.any(Object),
            orderTypeRef: expect.any(Object),
            orderChannelRef: expect.any(Object),
            tableName: expect.any(Object),
            tableGroupNumber: expect.any(Object),
            openTime: expect.any(Object),
            guestCount: expect.any(Object),
            language: expect.any(Object),
            isTrainingCheck: expect.any(Object),
            status: expect.any(Object),
            preparationStatus: expect.any(Object),
            subtotal: expect.any(Object),
            subtotalDiscountTotal: expect.any(Object),
            autoServiceChargeTotal: expect.any(Object),
            serviceChargeTotal: expect.any(Object),
            taxTotal: expect.any(Object),
            paymentTotal: expect.any(Object),
            totalDue: expect.any(Object),
            taxRateId: expect.any(Object),
          }),
        );
      });
    });

    describe('getCheckTrans', () => {
      it('should return NewCheckTrans for default CheckTrans initial value', () => {
        const formGroup = service.createCheckTransFormGroup(sampleWithNewData);

        const checkTrans = service.getCheckTrans(formGroup) as any;

        expect(checkTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewCheckTrans for empty CheckTrans initial value', () => {
        const formGroup = service.createCheckTransFormGroup();

        const checkTrans = service.getCheckTrans(formGroup) as any;

        expect(checkTrans).toMatchObject({});
      });

      it('should return ICheckTrans', () => {
        const formGroup = service.createCheckTransFormGroup(sampleWithRequiredData);

        const checkTrans = service.getCheckTrans(formGroup) as any;

        expect(checkTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICheckTrans should not enable id FormControl', () => {
        const formGroup = service.createCheckTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCheckTrans should disable id FormControl', () => {
        const formGroup = service.createCheckTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
