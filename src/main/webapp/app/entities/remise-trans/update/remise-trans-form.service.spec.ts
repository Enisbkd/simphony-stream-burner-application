import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../remise-trans.test-samples';

import { RemiseTransFormService } from './remise-trans-form.service';

describe('RemiseTrans Form Service', () => {
  let service: RemiseTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RemiseTransFormService);
  });

  describe('Service methods', () => {
    describe('createRemiseTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRemiseTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            discountId: expect.any(Object),
            frName: expect.any(Object),
            engName: expect.any(Object),
            discountType: expect.any(Object),
            discountValue: expect.any(Object),
          }),
        );
      });

      it('passing IRemiseTrans should create a new form with FormGroup', () => {
        const formGroup = service.createRemiseTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
            discountId: expect.any(Object),
            frName: expect.any(Object),
            engName: expect.any(Object),
            discountType: expect.any(Object),
            discountValue: expect.any(Object),
          }),
        );
      });
    });

    describe('getRemiseTrans', () => {
      it('should return NewRemiseTrans for default RemiseTrans initial value', () => {
        const formGroup = service.createRemiseTransFormGroup(sampleWithNewData);

        const remiseTrans = service.getRemiseTrans(formGroup) as any;

        expect(remiseTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewRemiseTrans for empty RemiseTrans initial value', () => {
        const formGroup = service.createRemiseTransFormGroup();

        const remiseTrans = service.getRemiseTrans(formGroup) as any;

        expect(remiseTrans).toMatchObject({});
      });

      it('should return IRemiseTrans', () => {
        const formGroup = service.createRemiseTransFormGroup(sampleWithRequiredData);

        const remiseTrans = service.getRemiseTrans(formGroup) as any;

        expect(remiseTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRemiseTrans should not enable id FormControl', () => {
        const formGroup = service.createRemiseTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRemiseTrans should disable id FormControl', () => {
        const formGroup = service.createRemiseTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
