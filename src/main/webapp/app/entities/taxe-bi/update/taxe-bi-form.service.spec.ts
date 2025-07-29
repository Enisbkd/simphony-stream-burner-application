import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../taxe-bi.test-samples';

import { TaxeBIFormService } from './taxe-bi-form.service';

describe('TaxeBI Form Service', () => {
  let service: TaxeBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaxeBIFormService);
  });

  describe('Service methods', () => {
    describe('createTaxeBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTaxeBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locRef: expect.any(Object),
            num: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
          }),
        );
      });

      it('passing ITaxeBI should create a new form with FormGroup', () => {
        const formGroup = service.createTaxeBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locRef: expect.any(Object),
            num: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
          }),
        );
      });
    });

    describe('getTaxeBI', () => {
      it('should return NewTaxeBI for default TaxeBI initial value', () => {
        const formGroup = service.createTaxeBIFormGroup(sampleWithNewData);

        const taxeBI = service.getTaxeBI(formGroup) as any;

        expect(taxeBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewTaxeBI for empty TaxeBI initial value', () => {
        const formGroup = service.createTaxeBIFormGroup();

        const taxeBI = service.getTaxeBI(formGroup) as any;

        expect(taxeBI).toMatchObject({});
      });

      it('should return ITaxeBI', () => {
        const formGroup = service.createTaxeBIFormGroup(sampleWithRequiredData);

        const taxeBI = service.getTaxeBI(formGroup) as any;

        expect(taxeBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITaxeBI should not enable id FormControl', () => {
        const formGroup = service.createTaxeBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTaxeBI should disable id FormControl', () => {
        const formGroup = service.createTaxeBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
