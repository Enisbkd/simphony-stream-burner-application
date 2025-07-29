import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IModePaiementTrans, NewModePaiementTrans } from '../mode-paiement-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IModePaiementTrans for edit and NewModePaiementTransFormGroupInput for create.
 */
type ModePaiementTransFormGroupInput = IModePaiementTrans | PartialWithRequiredKeyOf<NewModePaiementTrans>;

type ModePaiementTransFormDefaults = Pick<NewModePaiementTrans, 'id'>;

type ModePaiementTransFormGroupContent = {
  id: FormControl<IModePaiementTrans['id'] | NewModePaiementTrans['id']>;
  tenderId: FormControl<IModePaiementTrans['tenderId']>;
  name: FormControl<IModePaiementTrans['name']>;
  type: FormControl<IModePaiementTrans['type']>;
  extensions: FormControl<IModePaiementTrans['extensions']>;
  orgShortName: FormControl<IModePaiementTrans['orgShortName']>;
  locRef: FormControl<IModePaiementTrans['locRef']>;
  rvcRef: FormControl<IModePaiementTrans['rvcRef']>;
};

export type ModePaiementTransFormGroup = FormGroup<ModePaiementTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ModePaiementTransFormService {
  createModePaiementTransFormGroup(modePaiementTrans: ModePaiementTransFormGroupInput = { id: null }): ModePaiementTransFormGroup {
    const modePaiementTransRawValue = {
      ...this.getFormDefaults(),
      ...modePaiementTrans,
    };
    return new FormGroup<ModePaiementTransFormGroupContent>({
      id: new FormControl(
        { value: modePaiementTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tenderId: new FormControl(modePaiementTransRawValue.tenderId),
      name: new FormControl(modePaiementTransRawValue.name),
      type: new FormControl(modePaiementTransRawValue.type),
      extensions: new FormControl(modePaiementTransRawValue.extensions),
      orgShortName: new FormControl(modePaiementTransRawValue.orgShortName),
      locRef: new FormControl(modePaiementTransRawValue.locRef),
      rvcRef: new FormControl(modePaiementTransRawValue.rvcRef),
    });
  }

  getModePaiementTrans(form: ModePaiementTransFormGroup): IModePaiementTrans | NewModePaiementTrans {
    return form.getRawValue() as IModePaiementTrans | NewModePaiementTrans;
  }

  resetForm(form: ModePaiementTransFormGroup, modePaiementTrans: ModePaiementTransFormGroupInput): void {
    const modePaiementTransRawValue = { ...this.getFormDefaults(), ...modePaiementTrans };
    form.reset(
      {
        ...modePaiementTransRawValue,
        id: { value: modePaiementTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ModePaiementTransFormDefaults {
    return {
      id: null,
    };
  }
}
