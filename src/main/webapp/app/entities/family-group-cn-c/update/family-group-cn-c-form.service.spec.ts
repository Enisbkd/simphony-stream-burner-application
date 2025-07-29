import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../family-group-cn-c.test-samples';

import { FamilyGroupCnCFormService } from './family-group-cn-c-form.service';

describe('FamilyGroupCnC Form Service', () => {
  let service: FamilyGroupCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FamilyGroupCnCFormService);
  });

  describe('Service methods', () => {
    describe('createFamilyGroupCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            majorGroupRef: expect.any(Object),
          }),
        );
      });

      it('passing IFamilyGroupCnC should create a new form with FormGroup', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            majorGroupRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getFamilyGroupCnC', () => {
      it('should return NewFamilyGroupCnC for default FamilyGroupCnC initial value', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup(sampleWithNewData);

        const familyGroupCnC = service.getFamilyGroupCnC(formGroup) as any;

        expect(familyGroupCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewFamilyGroupCnC for empty FamilyGroupCnC initial value', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup();

        const familyGroupCnC = service.getFamilyGroupCnC(formGroup) as any;

        expect(familyGroupCnC).toMatchObject({});
      });

      it('should return IFamilyGroupCnC', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup(sampleWithRequiredData);

        const familyGroupCnC = service.getFamilyGroupCnC(formGroup) as any;

        expect(familyGroupCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFamilyGroupCnC should not enable id FormControl', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFamilyGroupCnC should disable id FormControl', () => {
        const formGroup = service.createFamilyGroupCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
