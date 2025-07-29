import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IModePaiementBI, NewModePaiementBI } from '../mode-paiement-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IModePaiementBI for edit and NewModePaiementBIFormGroupInput for create.
 */
type ModePaiementBIFormGroupInput = IModePaiementBI | PartialWithRequiredKeyOf<NewModePaiementBI>;

type ModePaiementBIFormDefaults = Pick<NewModePaiementBI, 'id'>;

type ModePaiementBIFormGroupContent = {
  id: FormControl<IModePaiementBI['id'] | NewModePaiementBI['id']>;
  locRef: FormControl<IModePaiementBI['locRef']>;
  num: FormControl<IModePaiementBI['num']>;
  name: FormControl<IModePaiementBI['name']>;
  type: FormControl<IModePaiementBI['type']>;
};

export type ModePaiementBIFormGroup = FormGroup<ModePaiementBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ModePaiementBIFormService {
  createModePaiementBIFormGroup(modePaiementBI: ModePaiementBIFormGroupInput = { id: null }): ModePaiementBIFormGroup {
    const modePaiementBIRawValue = {
      ...this.getFormDefaults(),
      ...modePaiementBI,
    };
    return new FormGroup<ModePaiementBIFormGroupContent>({
      id: new FormControl(
        { value: modePaiementBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      locRef: new FormControl(modePaiementBIRawValue.locRef),
      num: new FormControl(modePaiementBIRawValue.num),
      name: new FormControl(modePaiementBIRawValue.name),
      type: new FormControl(modePaiementBIRawValue.type),
    });
  }

  getModePaiementBI(form: ModePaiementBIFormGroup): IModePaiementBI | NewModePaiementBI {
    return form.getRawValue() as IModePaiementBI | NewModePaiementBI;
  }

  resetForm(form: ModePaiementBIFormGroup, modePaiementBI: ModePaiementBIFormGroupInput): void {
    const modePaiementBIRawValue = { ...this.getFormDefaults(), ...modePaiementBI };
    form.reset(
      {
        ...modePaiementBIRawValue,
        id: { value: modePaiementBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ModePaiementBIFormDefaults {
    return {
      id: null,
    };
  }
}
