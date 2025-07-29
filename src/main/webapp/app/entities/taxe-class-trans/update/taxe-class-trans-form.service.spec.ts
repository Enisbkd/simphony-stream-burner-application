import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../taxe-class-trans.test-samples';

import { TaxeClassTransFormService } from './taxe-class-trans-form.service';

describe('TaxeClassTrans Form Service', () => {
  let service: TaxeClassTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaxeClassTransFormService);
  });

  describe('Service methods', () => {
    describe('createTaxeClassTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTaxeClassTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            taxClassId: expect.any(Object),
            activeTaxRateRefs: expect.any(Object),
          }),
        );
      });

      it('passing ITaxeClassTrans should create a new form with FormGroup', () => {
        const formGroup = service.createTaxeClassTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            taxClassId: expect.any(Object),
            activeTaxRateRefs: expect.any(Object),
          }),
        );
      });
    });

    describe('getTaxeClassTrans', () => {
      it('should return NewTaxeClassTrans for default TaxeClassTrans initial value', () => {
        const formGroup = service.createTaxeClassTransFormGroup(sampleWithNewData);

        const taxeClassTrans = service.getTaxeClassTrans(formGroup) as any;

        expect(taxeClassTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewTaxeClassTrans for empty TaxeClassTrans initial value', () => {
        const formGroup = service.createTaxeClassTransFormGroup();

        const taxeClassTrans = service.getTaxeClassTrans(formGroup) as any;

        expect(taxeClassTrans).toMatchObject({});
      });

      it('should return ITaxeClassTrans', () => {
        const formGroup = service.createTaxeClassTransFormGroup(sampleWithRequiredData);

        const taxeClassTrans = service.getTaxeClassTrans(formGroup) as any;

        expect(taxeClassTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITaxeClassTrans should not enable id FormControl', () => {
        const formGroup = service.createTaxeClassTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTaxeClassTrans should disable id FormControl', () => {
        const formGroup = service.createTaxeClassTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
