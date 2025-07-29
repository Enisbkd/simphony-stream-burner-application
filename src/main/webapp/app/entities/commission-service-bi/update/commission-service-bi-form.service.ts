import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICommissionServiceBI, NewCommissionServiceBI } from '../commission-service-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommissionServiceBI for edit and NewCommissionServiceBIFormGroupInput for create.
 */
type CommissionServiceBIFormGroupInput = ICommissionServiceBI | PartialWithRequiredKeyOf<NewCommissionServiceBI>;

type CommissionServiceBIFormDefaults = Pick<NewCommissionServiceBI, 'id'>;

type CommissionServiceBIFormGroupContent = {
  id: FormControl<ICommissionServiceBI['id'] | NewCommissionServiceBI['id']>;
  nom: FormControl<ICommissionServiceBI['nom']>;
  nomCourt: FormControl<ICommissionServiceBI['nomCourt']>;
  typeValue: FormControl<ICommissionServiceBI['typeValue']>;
  value: FormControl<ICommissionServiceBI['value']>;
  etablissementRef: FormControl<ICommissionServiceBI['etablissementRef']>;
};

export type CommissionServiceBIFormGroup = FormGroup<CommissionServiceBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommissionServiceBIFormService {
  createCommissionServiceBIFormGroup(commissionServiceBI: CommissionServiceBIFormGroupInput = { id: null }): CommissionServiceBIFormGroup {
    const commissionServiceBIRawValue = {
      ...this.getFormDefaults(),
      ...commissionServiceBI,
    };
    return new FormGroup<CommissionServiceBIFormGroupContent>({
      id: new FormControl(
        { value: commissionServiceBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(commissionServiceBIRawValue.nom),
      nomCourt: new FormControl(commissionServiceBIRawValue.nomCourt),
      typeValue: new FormControl(commissionServiceBIRawValue.typeValue),
      value: new FormControl(commissionServiceBIRawValue.value),
      etablissementRef: new FormControl(commissionServiceBIRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getCommissionServiceBI(form: CommissionServiceBIFormGroup): ICommissionServiceBI | NewCommissionServiceBI {
    return form.getRawValue() as ICommissionServiceBI | NewCommissionServiceBI;
  }

  resetForm(form: CommissionServiceBIFormGroup, commissionServiceBI: CommissionServiceBIFormGroupInput): void {
    const commissionServiceBIRawValue = { ...this.getFormDefaults(), ...commissionServiceBI };
    form.reset(
      {
        ...commissionServiceBIRawValue,
        id: { value: commissionServiceBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CommissionServiceBIFormDefaults {
    return {
      id: null,
    };
  }
}
