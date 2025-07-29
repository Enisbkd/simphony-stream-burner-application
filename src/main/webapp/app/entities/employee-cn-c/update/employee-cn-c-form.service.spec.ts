import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../employee-cn-c.test-samples';

import { EmployeeCnCFormService } from './employee-cn-c-form.service';

describe('EmployeeCnC Form Service', () => {
  let service: EmployeeCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeCnCFormService);
  });

  describe('Service methods', () => {
    describe('createEmployeeCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmployeeCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            objectNum: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            checkName: expect.any(Object),
            email: expect.any(Object),
            languageObjNum: expect.any(Object),
            langId: expect.any(Object),
            alternateId: expect.any(Object),
            level: expect.any(Object),
            group: expect.any(Object),
            userName: expect.any(Object),
            firstRoleHierUnitId: expect.any(Object),
            firstRoleObjNum: expect.any(Object),
            firstVisibilityHierUnitId: expect.any(Object),
            firstVisibilityPropagateToChildren: expect.any(Object),
            firstPropertyHierUnitId: expect.any(Object),
            firstPropertyObjNum: expect.any(Object),
            firstPropertyEmpClassObjNum: expect.any(Object),
            firstPropertyOptions: expect.any(Object),
            firstOperatorRvcHierUnitId: expect.any(Object),
            firstOperatorRvcObjNum: expect.any(Object),
            firstOperatorOptions: expect.any(Object),
            firstOperatorCashDrawerObjNum: expect.any(Object),
            firstOperatorTmsColorObjNum: expect.any(Object),
            firstOperatorServerEfficiency: expect.any(Object),
            pin: expect.any(Object),
            accessId: expect.any(Object),
          }),
        );
      });

      it('passing IEmployeeCnC should create a new form with FormGroup', () => {
        const formGroup = service.createEmployeeCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            objectNum: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            checkName: expect.any(Object),
            email: expect.any(Object),
            languageObjNum: expect.any(Object),
            langId: expect.any(Object),
            alternateId: expect.any(Object),
            level: expect.any(Object),
            group: expect.any(Object),
            userName: expect.any(Object),
            firstRoleHierUnitId: expect.any(Object),
            firstRoleObjNum: expect.any(Object),
            firstVisibilityHierUnitId: expect.any(Object),
            firstVisibilityPropagateToChildren: expect.any(Object),
            firstPropertyHierUnitId: expect.any(Object),
            firstPropertyObjNum: expect.any(Object),
            firstPropertyEmpClassObjNum: expect.any(Object),
            firstPropertyOptions: expect.any(Object),
            firstOperatorRvcHierUnitId: expect.any(Object),
            firstOperatorRvcObjNum: expect.any(Object),
            firstOperatorOptions: expect.any(Object),
            firstOperatorCashDrawerObjNum: expect.any(Object),
            firstOperatorTmsColorObjNum: expect.any(Object),
            firstOperatorServerEfficiency: expect.any(Object),
            pin: expect.any(Object),
            accessId: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmployeeCnC', () => {
      it('should return NewEmployeeCnC for default EmployeeCnC initial value', () => {
        const formGroup = service.createEmployeeCnCFormGroup(sampleWithNewData);

        const employeeCnC = service.getEmployeeCnC(formGroup) as any;

        expect(employeeCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmployeeCnC for empty EmployeeCnC initial value', () => {
        const formGroup = service.createEmployeeCnCFormGroup();

        const employeeCnC = service.getEmployeeCnC(formGroup) as any;

        expect(employeeCnC).toMatchObject({});
      });

      it('should return IEmployeeCnC', () => {
        const formGroup = service.createEmployeeCnCFormGroup(sampleWithRequiredData);

        const employeeCnC = service.getEmployeeCnC(formGroup) as any;

        expect(employeeCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmployeeCnC should not enable id FormControl', () => {
        const formGroup = service.createEmployeeCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmployeeCnC should disable id FormControl', () => {
        const formGroup = service.createEmployeeCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
