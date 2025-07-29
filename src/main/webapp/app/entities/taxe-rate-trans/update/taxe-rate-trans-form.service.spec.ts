import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../taxe-rate-trans.test-samples';

import { TaxeRateTransFormService } from './taxe-rate-trans-form.service';

describe('TaxeRateTrans Form Service', () => {
  let service: TaxeRateTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaxeRateTransFormService);
  });

  describe('Service methods', () => {
    describe('createTaxeRateTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTaxeRateTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            taxRateId: expect.any(Object),
            percentage: expect.any(Object),
            taxType: expect.any(Object),
            nameFR: expect.any(Object),
            nameEN: expect.any(Object),
          }),
        );
      });

      it('passing ITaxeRateTrans should create a new form with FormGroup', () => {
        const formGroup = service.createTaxeRateTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            taxRateId: expect.any(Object),
            percentage: expect.any(Object),
            taxType: expect.any(Object),
            nameFR: expect.any(Object),
            nameEN: expect.any(Object),
          }),
        );
      });
    });

    describe('getTaxeRateTrans', () => {
      it('should return NewTaxeRateTrans for default TaxeRateTrans initial value', () => {
        const formGroup = service.createTaxeRateTransFormGroup(sampleWithNewData);

        const taxeRateTrans = service.getTaxeRateTrans(formGroup) as any;

        expect(taxeRateTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewTaxeRateTrans for empty TaxeRateTrans initial value', () => {
        const formGroup = service.createTaxeRateTransFormGroup();

        const taxeRateTrans = service.getTaxeRateTrans(formGroup) as any;

        expect(taxeRateTrans).toMatchObject({});
      });

      it('should return ITaxeRateTrans', () => {
        const formGroup = service.createTaxeRateTransFormGroup(sampleWithRequiredData);

        const taxeRateTrans = service.getTaxeRateTrans(formGroup) as any;

        expect(taxeRateTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITaxeRateTrans should not enable id FormControl', () => {
        const formGroup = service.createTaxeRateTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTaxeRateTrans should disable id FormControl', () => {
        const formGroup = service.createTaxeRateTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
