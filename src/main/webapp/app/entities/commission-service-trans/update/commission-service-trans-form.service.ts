import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICommissionServiceTrans, NewCommissionServiceTrans } from '../commission-service-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommissionServiceTrans for edit and NewCommissionServiceTransFormGroupInput for create.
 */
type CommissionServiceTransFormGroupInput = ICommissionServiceTrans | PartialWithRequiredKeyOf<NewCommissionServiceTrans>;

type CommissionServiceTransFormDefaults = Pick<NewCommissionServiceTrans, 'id'>;

type CommissionServiceTransFormGroupContent = {
  id: FormControl<ICommissionServiceTrans['id'] | NewCommissionServiceTrans['id']>;
  orgShortName: FormControl<ICommissionServiceTrans['orgShortName']>;
  locRef: FormControl<ICommissionServiceTrans['locRef']>;
  rvcRef: FormControl<ICommissionServiceTrans['rvcRef']>;
  serviceChargeId: FormControl<ICommissionServiceTrans['serviceChargeId']>;
  name: FormControl<ICommissionServiceTrans['name']>;
  type: FormControl<ICommissionServiceTrans['type']>;
  value: FormControl<ICommissionServiceTrans['value']>;
};

export type CommissionServiceTransFormGroup = FormGroup<CommissionServiceTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommissionServiceTransFormService {
  createCommissionServiceTransFormGroup(
    commissionServiceTrans: CommissionServiceTransFormGroupInput = { id: null },
  ): CommissionServiceTransFormGroup {
    const commissionServiceTransRawValue = {
      ...this.getFormDefaults(),
      ...commissionServiceTrans,
    };
    return new FormGroup<CommissionServiceTransFormGroupContent>({
      id: new FormControl(
        { value: commissionServiceTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      orgShortName: new FormControl(commissionServiceTransRawValue.orgShortName),
      locRef: new FormControl(commissionServiceTransRawValue.locRef),
      rvcRef: new FormControl(commissionServiceTransRawValue.rvcRef),
      serviceChargeId: new FormControl(commissionServiceTransRawValue.serviceChargeId),
      name: new FormControl(commissionServiceTransRawValue.name),
      type: new FormControl(commissionServiceTransRawValue.type),
      value: new FormControl(commissionServiceTransRawValue.value),
    });
  }

  getCommissionServiceTrans(form: CommissionServiceTransFormGroup): ICommissionServiceTrans | NewCommissionServiceTrans {
    return form.getRawValue() as ICommissionServiceTrans | NewCommissionServiceTrans;
  }

  resetForm(form: CommissionServiceTransFormGroup, commissionServiceTrans: CommissionServiceTransFormGroupInput): void {
    const commissionServiceTransRawValue = { ...this.getFormDefaults(), ...commissionServiceTrans };
    form.reset(
      {
        ...commissionServiceTransRawValue,
        id: { value: commissionServiceTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CommissionServiceTransFormDefaults {
    return {
      id: null,
    };
  }
}
