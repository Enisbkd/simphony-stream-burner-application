import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../societe-trans.test-samples';

import { SocieteTransFormService } from './societe-trans-form.service';

describe('SocieteTrans Form Service', () => {
  let service: SocieteTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocieteTransFormService);
  });

  describe('Service methods', () => {
    describe('createSocieteTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocieteTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
          }),
        );
      });

      it('passing ISocieteTrans should create a new form with FormGroup', () => {
        const formGroup = service.createSocieteTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
          }),
        );
      });
    });

    describe('getSocieteTrans', () => {
      it('should return NewSocieteTrans for default SocieteTrans initial value', () => {
        const formGroup = service.createSocieteTransFormGroup(sampleWithNewData);

        const societeTrans = service.getSocieteTrans(formGroup) as any;

        expect(societeTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocieteTrans for empty SocieteTrans initial value', () => {
        const formGroup = service.createSocieteTransFormGroup();

        const societeTrans = service.getSocieteTrans(formGroup) as any;

        expect(societeTrans).toMatchObject({});
      });

      it('should return ISocieteTrans', () => {
        const formGroup = service.createSocieteTransFormGroup(sampleWithRequiredData);

        const societeTrans = service.getSocieteTrans(formGroup) as any;

        expect(societeTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocieteTrans should not enable id FormControl', () => {
        const formGroup = service.createSocieteTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocieteTrans should disable id FormControl', () => {
        const formGroup = service.createSocieteTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
