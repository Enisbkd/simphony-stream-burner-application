import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmployeeCnC, NewEmployeeCnC } from '../employee-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployeeCnC for edit and NewEmployeeCnCFormGroupInput for create.
 */
type EmployeeCnCFormGroupInput = IEmployeeCnC | PartialWithRequiredKeyOf<NewEmployeeCnC>;

type EmployeeCnCFormDefaults = Pick<NewEmployeeCnC, 'id' | 'firstVisibilityPropagateToChildren'>;

type EmployeeCnCFormGroupContent = {
  id: FormControl<IEmployeeCnC['id'] | NewEmployeeCnC['id']>;
  objectNum: FormControl<IEmployeeCnC['objectNum']>;
  firstName: FormControl<IEmployeeCnC['firstName']>;
  lastName: FormControl<IEmployeeCnC['lastName']>;
  checkName: FormControl<IEmployeeCnC['checkName']>;
  email: FormControl<IEmployeeCnC['email']>;
  languageObjNum: FormControl<IEmployeeCnC['languageObjNum']>;
  langId: FormControl<IEmployeeCnC['langId']>;
  alternateId: FormControl<IEmployeeCnC['alternateId']>;
  level: FormControl<IEmployeeCnC['level']>;
  group: FormControl<IEmployeeCnC['group']>;
  userName: FormControl<IEmployeeCnC['userName']>;
  firstRoleHierUnitId: FormControl<IEmployeeCnC['firstRoleHierUnitId']>;
  firstRoleObjNum: FormControl<IEmployeeCnC['firstRoleObjNum']>;
  firstVisibilityHierUnitId: FormControl<IEmployeeCnC['firstVisibilityHierUnitId']>;
  firstVisibilityPropagateToChildren: FormControl<IEmployeeCnC['firstVisibilityPropagateToChildren']>;
  firstPropertyHierUnitId: FormControl<IEmployeeCnC['firstPropertyHierUnitId']>;
  firstPropertyObjNum: FormControl<IEmployeeCnC['firstPropertyObjNum']>;
  firstPropertyEmpClassObjNum: FormControl<IEmployeeCnC['firstPropertyEmpClassObjNum']>;
  firstPropertyOptions: FormControl<IEmployeeCnC['firstPropertyOptions']>;
  firstOperatorRvcHierUnitId: FormControl<IEmployeeCnC['firstOperatorRvcHierUnitId']>;
  firstOperatorRvcObjNum: FormControl<IEmployeeCnC['firstOperatorRvcObjNum']>;
  firstOperatorOptions: FormControl<IEmployeeCnC['firstOperatorOptions']>;
  firstOperatorCashDrawerObjNum: FormControl<IEmployeeCnC['firstOperatorCashDrawerObjNum']>;
  firstOperatorTmsColorObjNum: FormControl<IEmployeeCnC['firstOperatorTmsColorObjNum']>;
  firstOperatorServerEfficiency: FormControl<IEmployeeCnC['firstOperatorServerEfficiency']>;
  pin: FormControl<IEmployeeCnC['pin']>;
  accessId: FormControl<IEmployeeCnC['accessId']>;
};

export type EmployeeCnCFormGroup = FormGroup<EmployeeCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeCnCFormService {
  createEmployeeCnCFormGroup(employeeCnC: EmployeeCnCFormGroupInput = { id: null }): EmployeeCnCFormGroup {
    const employeeCnCRawValue = {
      ...this.getFormDefaults(),
      ...employeeCnC,
    };
    return new FormGroup<EmployeeCnCFormGroupContent>({
      id: new FormControl(
        { value: employeeCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      objectNum: new FormControl(employeeCnCRawValue.objectNum),
      firstName: new FormControl(employeeCnCRawValue.firstName),
      lastName: new FormControl(employeeCnCRawValue.lastName),
      checkName: new FormControl(employeeCnCRawValue.checkName),
      email: new FormControl(employeeCnCRawValue.email),
      languageObjNum: new FormControl(employeeCnCRawValue.languageObjNum),
      langId: new FormControl(employeeCnCRawValue.langId),
      alternateId: new FormControl(employeeCnCRawValue.alternateId),
      level: new FormControl(employeeCnCRawValue.level),
      group: new FormControl(employeeCnCRawValue.group),
      userName: new FormControl(employeeCnCRawValue.userName),
      firstRoleHierUnitId: new FormControl(employeeCnCRawValue.firstRoleHierUnitId),
      firstRoleObjNum: new FormControl(employeeCnCRawValue.firstRoleObjNum),
      firstVisibilityHierUnitId: new FormControl(employeeCnCRawValue.firstVisibilityHierUnitId),
      firstVisibilityPropagateToChildren: new FormControl(employeeCnCRawValue.firstVisibilityPropagateToChildren),
      firstPropertyHierUnitId: new FormControl(employeeCnCRawValue.firstPropertyHierUnitId),
      firstPropertyObjNum: new FormControl(employeeCnCRawValue.firstPropertyObjNum),
      firstPropertyEmpClassObjNum: new FormControl(employeeCnCRawValue.firstPropertyEmpClassObjNum),
      firstPropertyOptions: new FormControl(employeeCnCRawValue.firstPropertyOptions),
      firstOperatorRvcHierUnitId: new FormControl(employeeCnCRawValue.firstOperatorRvcHierUnitId),
      firstOperatorRvcObjNum: new FormControl(employeeCnCRawValue.firstOperatorRvcObjNum),
      firstOperatorOptions: new FormControl(employeeCnCRawValue.firstOperatorOptions),
      firstOperatorCashDrawerObjNum: new FormControl(employeeCnCRawValue.firstOperatorCashDrawerObjNum),
      firstOperatorTmsColorObjNum: new FormControl(employeeCnCRawValue.firstOperatorTmsColorObjNum),
      firstOperatorServerEfficiency: new FormControl(employeeCnCRawValue.firstOperatorServerEfficiency),
      pin: new FormControl(employeeCnCRawValue.pin),
      accessId: new FormControl(employeeCnCRawValue.accessId),
    });
  }

  getEmployeeCnC(form: EmployeeCnCFormGroup): IEmployeeCnC | NewEmployeeCnC {
    return form.getRawValue() as IEmployeeCnC | NewEmployeeCnC;
  }

  resetForm(form: EmployeeCnCFormGroup, employeeCnC: EmployeeCnCFormGroupInput): void {
    const employeeCnCRawValue = { ...this.getFormDefaults(), ...employeeCnC };
    form.reset(
      {
        ...employeeCnCRawValue,
        id: { value: employeeCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeeCnCFormDefaults {
    return {
      id: null,
      firstVisibilityPropagateToChildren: false,
    };
  }
}
