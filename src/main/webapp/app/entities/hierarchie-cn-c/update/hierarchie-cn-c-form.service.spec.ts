import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../hierarchie-cn-c.test-samples';

import { HierarchieCnCFormService } from './hierarchie-cn-c-form.service';

describe('HierarchieCnC Form Service', () => {
  let service: HierarchieCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HierarchieCnCFormService);
  });

  describe('Service methods', () => {
    describe('createHierarchieCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHierarchieCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            parentHierId: expect.any(Object),
            unitId: expect.any(Object),
          }),
        );
      });

      it('passing IHierarchieCnC should create a new form with FormGroup', () => {
        const formGroup = service.createHierarchieCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            parentHierId: expect.any(Object),
            unitId: expect.any(Object),
          }),
        );
      });
    });

    describe('getHierarchieCnC', () => {
      it('should return NewHierarchieCnC for default HierarchieCnC initial value', () => {
        const formGroup = service.createHierarchieCnCFormGroup(sampleWithNewData);

        const hierarchieCnC = service.getHierarchieCnC(formGroup) as any;

        expect(hierarchieCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewHierarchieCnC for empty HierarchieCnC initial value', () => {
        const formGroup = service.createHierarchieCnCFormGroup();

        const hierarchieCnC = service.getHierarchieCnC(formGroup) as any;

        expect(hierarchieCnC).toMatchObject({});
      });

      it('should return IHierarchieCnC', () => {
        const formGroup = service.createHierarchieCnCFormGroup(sampleWithRequiredData);

        const hierarchieCnC = service.getHierarchieCnC(formGroup) as any;

        expect(hierarchieCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHierarchieCnC should not enable id FormControl', () => {
        const formGroup = service.createHierarchieCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHierarchieCnC should disable id FormControl', () => {
        const formGroup = service.createHierarchieCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
