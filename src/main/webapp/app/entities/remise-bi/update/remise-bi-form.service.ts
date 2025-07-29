import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRemiseBI, NewRemiseBI } from '../remise-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRemiseBI for edit and NewRemiseBIFormGroupInput for create.
 */
type RemiseBIFormGroupInput = IRemiseBI | PartialWithRequiredKeyOf<NewRemiseBI>;

type RemiseBIFormDefaults = Pick<NewRemiseBI, 'id'>;

type RemiseBIFormGroupContent = {
  id: FormControl<IRemiseBI['id'] | NewRemiseBI['id']>;
  num: FormControl<IRemiseBI['num']>;
  name: FormControl<IRemiseBI['name']>;
  posPercent: FormControl<IRemiseBI['posPercent']>;
  rptGrpNum: FormControl<IRemiseBI['rptGrpNum']>;
  rptGrpName: FormControl<IRemiseBI['rptGrpName']>;
  locRef: FormControl<IRemiseBI['locRef']>;
};

export type RemiseBIFormGroup = FormGroup<RemiseBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RemiseBIFormService {
  createRemiseBIFormGroup(remiseBI: RemiseBIFormGroupInput = { id: null }): RemiseBIFormGroup {
    const remiseBIRawValue = {
      ...this.getFormDefaults(),
      ...remiseBI,
    };
    return new FormGroup<RemiseBIFormGroupContent>({
      id: new FormControl(
        { value: remiseBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(remiseBIRawValue.num),
      name: new FormControl(remiseBIRawValue.name),
      posPercent: new FormControl(remiseBIRawValue.posPercent),
      rptGrpNum: new FormControl(remiseBIRawValue.rptGrpNum),
      rptGrpName: new FormControl(remiseBIRawValue.rptGrpName),
      locRef: new FormControl(remiseBIRawValue.locRef),
    });
  }

  getRemiseBI(form: RemiseBIFormGroup): IRemiseBI | NewRemiseBI {
    return form.getRawValue() as IRemiseBI | NewRemiseBI;
  }

  resetForm(form: RemiseBIFormGroup, remiseBI: RemiseBIFormGroupInput): void {
    const remiseBIRawValue = { ...this.getFormDefaults(), ...remiseBI };
    form.reset(
      {
        ...remiseBIRawValue,
        id: { value: remiseBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RemiseBIFormDefaults {
    return {
      id: null,
    };
  }
}
