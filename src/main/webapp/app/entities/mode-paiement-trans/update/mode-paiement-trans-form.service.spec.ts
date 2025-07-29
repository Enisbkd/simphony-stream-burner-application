import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../mode-paiement-trans.test-samples';

import { ModePaiementTransFormService } from './mode-paiement-trans-form.service';

describe('ModePaiementTrans Form Service', () => {
  let service: ModePaiementTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModePaiementTransFormService);
  });

  describe('Service methods', () => {
    describe('createModePaiementTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createModePaiementTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tenderId: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            extensions: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
          }),
        );
      });

      it('passing IModePaiementTrans should create a new form with FormGroup', () => {
        const formGroup = service.createModePaiementTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tenderId: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            extensions: expect.any(Object),
            orgShortName: expect.any(Object),
            locRef: expect.any(Object),
            rvcRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getModePaiementTrans', () => {
      it('should return NewModePaiementTrans for default ModePaiementTrans initial value', () => {
        const formGroup = service.createModePaiementTransFormGroup(sampleWithNewData);

        const modePaiementTrans = service.getModePaiementTrans(formGroup) as any;

        expect(modePaiementTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewModePaiementTrans for empty ModePaiementTrans initial value', () => {
        const formGroup = service.createModePaiementTransFormGroup();

        const modePaiementTrans = service.getModePaiementTrans(formGroup) as any;

        expect(modePaiementTrans).toMatchObject({});
      });

      it('should return IModePaiementTrans', () => {
        const formGroup = service.createModePaiementTransFormGroup(sampleWithRequiredData);

        const modePaiementTrans = service.getModePaiementTrans(formGroup) as any;

        expect(modePaiementTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IModePaiementTrans should not enable id FormControl', () => {
        const formGroup = service.createModePaiementTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewModePaiementTrans should disable id FormControl', () => {
        const formGroup = service.createModePaiementTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
