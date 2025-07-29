import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../commission-service-bi.test-samples';

import { CommissionServiceBIFormService } from './commission-service-bi-form.service';

describe('CommissionServiceBI Form Service', () => {
  let service: CommissionServiceBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommissionServiceBIFormService);
  });

  describe('Service methods', () => {
    describe('createCommissionServiceBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommissionServiceBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            typeValue: expect.any(Object),
            value: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing ICommissionServiceBI should create a new form with FormGroup', () => {
        const formGroup = service.createCommissionServiceBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            typeValue: expect.any(Object),
            value: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getCommissionServiceBI', () => {
      it('should return NewCommissionServiceBI for default CommissionServiceBI initial value', () => {
        const formGroup = service.createCommissionServiceBIFormGroup(sampleWithNewData);

        const commissionServiceBI = service.getCommissionServiceBI(formGroup) as any;

        expect(commissionServiceBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommissionServiceBI for empty CommissionServiceBI initial value', () => {
        const formGroup = service.createCommissionServiceBIFormGroup();

        const commissionServiceBI = service.getCommissionServiceBI(formGroup) as any;

        expect(commissionServiceBI).toMatchObject({});
      });

      it('should return ICommissionServiceBI', () => {
        const formGroup = service.createCommissionServiceBIFormGroup(sampleWithRequiredData);

        const commissionServiceBI = service.getCommissionServiceBI(formGroup) as any;

        expect(commissionServiceBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommissionServiceBI should not enable id FormControl', () => {
        const formGroup = service.createCommissionServiceBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommissionServiceBI should disable id FormControl', () => {
        const formGroup = service.createCommissionServiceBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
