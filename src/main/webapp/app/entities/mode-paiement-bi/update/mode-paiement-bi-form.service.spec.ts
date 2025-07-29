import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../mode-paiement-bi.test-samples';

import { ModePaiementBIFormService } from './mode-paiement-bi-form.service';

describe('ModePaiementBI Form Service', () => {
  let service: ModePaiementBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModePaiementBIFormService);
  });

  describe('Service methods', () => {
    describe('createModePaiementBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createModePaiementBIFormGroup();

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

      it('passing IModePaiementBI should create a new form with FormGroup', () => {
        const formGroup = service.createModePaiementBIFormGroup(sampleWithRequiredData);

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

    describe('getModePaiementBI', () => {
      it('should return NewModePaiementBI for default ModePaiementBI initial value', () => {
        const formGroup = service.createModePaiementBIFormGroup(sampleWithNewData);

        const modePaiementBI = service.getModePaiementBI(formGroup) as any;

        expect(modePaiementBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewModePaiementBI for empty ModePaiementBI initial value', () => {
        const formGroup = service.createModePaiementBIFormGroup();

        const modePaiementBI = service.getModePaiementBI(formGroup) as any;

        expect(modePaiementBI).toMatchObject({});
      });

      it('should return IModePaiementBI', () => {
        const formGroup = service.createModePaiementBIFormGroup(sampleWithRequiredData);

        const modePaiementBI = service.getModePaiementBI(formGroup) as any;

        expect(modePaiementBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IModePaiementBI should not enable id FormControl', () => {
        const formGroup = service.createModePaiementBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewModePaiementBI should disable id FormControl', () => {
        const formGroup = service.createModePaiementBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
