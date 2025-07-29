import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISocieteTrans, NewSocieteTrans } from '../societe-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocieteTrans for edit and NewSocieteTransFormGroupInput for create.
 */
type SocieteTransFormGroupInput = ISocieteTrans | PartialWithRequiredKeyOf<NewSocieteTrans>;

type SocieteTransFormDefaults = Pick<NewSocieteTrans, 'id'>;

type SocieteTransFormGroupContent = {
  id: FormControl<ISocieteTrans['id'] | NewSocieteTrans['id']>;
  nom: FormControl<ISocieteTrans['nom']>;
  nomCourt: FormControl<ISocieteTrans['nomCourt']>;
};

export type SocieteTransFormGroup = FormGroup<SocieteTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocieteTransFormService {
  createSocieteTransFormGroup(societeTrans: SocieteTransFormGroupInput = { id: null }): SocieteTransFormGroup {
    const societeTransRawValue = {
      ...this.getFormDefaults(),
      ...societeTrans,
    };
    return new FormGroup<SocieteTransFormGroupContent>({
      id: new FormControl(
        { value: societeTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(societeTransRawValue.nom),
      nomCourt: new FormControl(societeTransRawValue.nomCourt),
    });
  }

  getSocieteTrans(form: SocieteTransFormGroup): ISocieteTrans | NewSocieteTrans {
    return form.getRawValue() as ISocieteTrans | NewSocieteTrans;
  }

  resetForm(form: SocieteTransFormGroup, societeTrans: SocieteTransFormGroupInput): void {
    const societeTransRawValue = { ...this.getFormDefaults(), ...societeTrans };
    form.reset(
      {
        ...societeTransRawValue,
        id: { value: societeTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SocieteTransFormDefaults {
    return {
      id: null,
    };
  }
}
