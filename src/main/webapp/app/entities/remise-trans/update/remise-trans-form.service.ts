import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRemiseTrans, NewRemiseTrans } from '../remise-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRemiseTrans for edit and NewRemiseTransFormGroupInput for create.
 */
type RemiseTransFormGroupInput = IRemiseTrans | PartialWithRequiredKeyOf<NewRemiseTrans>;

type RemiseTransFormDefaults = Pick<NewRemiseTrans, 'id'>;

type RemiseTransFormGroupContent = {
  id: FormControl<IRemiseTrans['id'] | NewRemiseTrans['id']>;
  orgShortName: FormControl<IRemiseTrans['orgShortName']>;
  locRef: FormControl<IRemiseTrans['locRef']>;
  rvcRef: FormControl<IRemiseTrans['rvcRef']>;
  discountId: FormControl<IRemiseTrans['discountId']>;
  frName: FormControl<IRemiseTrans['frName']>;
  engName: FormControl<IRemiseTrans['engName']>;
  discountType: FormControl<IRemiseTrans['discountType']>;
  discountValue: FormControl<IRemiseTrans['discountValue']>;
};

export type RemiseTransFormGroup = FormGroup<RemiseTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RemiseTransFormService {
  createRemiseTransFormGroup(remiseTrans: RemiseTransFormGroupInput = { id: null }): RemiseTransFormGroup {
    const remiseTransRawValue = {
      ...this.getFormDefaults(),
      ...remiseTrans,
    };
    return new FormGroup<RemiseTransFormGroupContent>({
      id: new FormControl(
        { value: remiseTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      orgShortName: new FormControl(remiseTransRawValue.orgShortName),
      locRef: new FormControl(remiseTransRawValue.locRef),
      rvcRef: new FormControl(remiseTransRawValue.rvcRef),
      discountId: new FormControl(remiseTransRawValue.discountId),
      frName: new FormControl(remiseTransRawValue.frName),
      engName: new FormControl(remiseTransRawValue.engName),
      discountType: new FormControl(remiseTransRawValue.discountType),
      discountValue: new FormControl(remiseTransRawValue.discountValue),
    });
  }

  getRemiseTrans(form: RemiseTransFormGroup): IRemiseTrans | NewRemiseTrans {
    return form.getRawValue() as IRemiseTrans | NewRemiseTrans;
  }

  resetForm(form: RemiseTransFormGroup, remiseTrans: RemiseTransFormGroupInput): void {
    const remiseTransRawValue = { ...this.getFormDefaults(), ...remiseTrans };
    form.reset(
      {
        ...remiseTransRawValue,
        id: { value: remiseTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RemiseTransFormDefaults {
    return {
      id: null,
    };
  }
}
