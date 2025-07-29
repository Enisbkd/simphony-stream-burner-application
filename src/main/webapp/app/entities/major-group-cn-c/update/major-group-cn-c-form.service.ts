import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMajorGroupCnC, NewMajorGroupCnC } from '../major-group-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMajorGroupCnC for edit and NewMajorGroupCnCFormGroupInput for create.
 */
type MajorGroupCnCFormGroupInput = IMajorGroupCnC | PartialWithRequiredKeyOf<NewMajorGroupCnC>;

type MajorGroupCnCFormDefaults = Pick<NewMajorGroupCnC, 'id'>;

type MajorGroupCnCFormGroupContent = {
  id: FormControl<IMajorGroupCnC['id'] | NewMajorGroupCnC['id']>;
  nom: FormControl<IMajorGroupCnC['nom']>;
  nomCourt: FormControl<IMajorGroupCnC['nomCourt']>;
  pointDeVenteRef: FormControl<IMajorGroupCnC['pointDeVenteRef']>;
};

export type MajorGroupCnCFormGroup = FormGroup<MajorGroupCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MajorGroupCnCFormService {
  createMajorGroupCnCFormGroup(majorGroupCnC: MajorGroupCnCFormGroupInput = { id: null }): MajorGroupCnCFormGroup {
    const majorGroupCnCRawValue = {
      ...this.getFormDefaults(),
      ...majorGroupCnC,
    };
    return new FormGroup<MajorGroupCnCFormGroupContent>({
      id: new FormControl(
        { value: majorGroupCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(majorGroupCnCRawValue.nom),
      nomCourt: new FormControl(majorGroupCnCRawValue.nomCourt),
      pointDeVenteRef: new FormControl(majorGroupCnCRawValue.pointDeVenteRef),
    });
  }

  getMajorGroupCnC(form: MajorGroupCnCFormGroup): IMajorGroupCnC | NewMajorGroupCnC {
    return form.getRawValue() as IMajorGroupCnC | NewMajorGroupCnC;
  }

  resetForm(form: MajorGroupCnCFormGroup, majorGroupCnC: MajorGroupCnCFormGroupInput): void {
    const majorGroupCnCRawValue = { ...this.getFormDefaults(), ...majorGroupCnC };
    form.reset(
      {
        ...majorGroupCnCRawValue,
        id: { value: majorGroupCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MajorGroupCnCFormDefaults {
    return {
      id: null,
    };
  }
}
