import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../bar-code-trans.test-samples';

import { BarCodeTransFormService } from './bar-code-trans-form.service';

describe('BarCodeTrans Form Service', () => {
  let service: BarCodeTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BarCodeTransFormService);
  });

  describe('Service methods', () => {
    describe('createBarCodeTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBarCodeTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            barcodeId: expect.any(Object),
            barcode: expect.any(Object),
            menuItemId: expect.any(Object),
            defenitionSequence: expect.any(Object),
            price: expect.any(Object),
            priceSequence: expect.any(Object),
            preparationCost: expect.any(Object),
          }),
        );
      });

      it('passing IBarCodeTrans should create a new form with FormGroup', () => {
        const formGroup = service.createBarCodeTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            barcodeId: expect.any(Object),
            barcode: expect.any(Object),
            menuItemId: expect.any(Object),
            defenitionSequence: expect.any(Object),
            price: expect.any(Object),
            priceSequence: expect.any(Object),
            preparationCost: expect.any(Object),
          }),
        );
      });
    });

    describe('getBarCodeTrans', () => {
      it('should return NewBarCodeTrans for default BarCodeTrans initial value', () => {
        const formGroup = service.createBarCodeTransFormGroup(sampleWithNewData);

        const barCodeTrans = service.getBarCodeTrans(formGroup) as any;

        expect(barCodeTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewBarCodeTrans for empty BarCodeTrans initial value', () => {
        const formGroup = service.createBarCodeTransFormGroup();

        const barCodeTrans = service.getBarCodeTrans(formGroup) as any;

        expect(barCodeTrans).toMatchObject({});
      });

      it('should return IBarCodeTrans', () => {
        const formGroup = service.createBarCodeTransFormGroup(sampleWithRequiredData);

        const barCodeTrans = service.getBarCodeTrans(formGroup) as any;

        expect(barCodeTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBarCodeTrans should not enable id FormControl', () => {
        const formGroup = service.createBarCodeTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBarCodeTrans should disable id FormControl', () => {
        const formGroup = service.createBarCodeTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
