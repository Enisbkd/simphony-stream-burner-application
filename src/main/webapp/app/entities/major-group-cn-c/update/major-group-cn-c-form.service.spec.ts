import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../major-group-cn-c.test-samples';

import { MajorGroupCnCFormService } from './major-group-cn-c-form.service';

describe('MajorGroupCnC Form Service', () => {
  let service: MajorGroupCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MajorGroupCnCFormService);
  });

  describe('Service methods', () => {
    describe('createMajorGroupCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMajorGroupCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });

      it('passing IMajorGroupCnC should create a new form with FormGroup', () => {
        const formGroup = service.createMajorGroupCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getMajorGroupCnC', () => {
      it('should return NewMajorGroupCnC for default MajorGroupCnC initial value', () => {
        const formGroup = service.createMajorGroupCnCFormGroup(sampleWithNewData);

        const majorGroupCnC = service.getMajorGroupCnC(formGroup) as any;

        expect(majorGroupCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewMajorGroupCnC for empty MajorGroupCnC initial value', () => {
        const formGroup = service.createMajorGroupCnCFormGroup();

        const majorGroupCnC = service.getMajorGroupCnC(formGroup) as any;

        expect(majorGroupCnC).toMatchObject({});
      });

      it('should return IMajorGroupCnC', () => {
        const formGroup = service.createMajorGroupCnCFormGroup(sampleWithRequiredData);

        const majorGroupCnC = service.getMajorGroupCnC(formGroup) as any;

        expect(majorGroupCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMajorGroupCnC should not enable id FormControl', () => {
        const formGroup = service.createMajorGroupCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMajorGroupCnC should disable id FormControl', () => {
        const formGroup = service.createMajorGroupCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
