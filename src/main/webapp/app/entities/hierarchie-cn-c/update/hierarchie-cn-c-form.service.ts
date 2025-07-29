import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IHierarchieCnC, NewHierarchieCnC } from '../hierarchie-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHierarchieCnC for edit and NewHierarchieCnCFormGroupInput for create.
 */
type HierarchieCnCFormGroupInput = IHierarchieCnC | PartialWithRequiredKeyOf<NewHierarchieCnC>;

type HierarchieCnCFormDefaults = Pick<NewHierarchieCnC, 'id'>;

type HierarchieCnCFormGroupContent = {
  id: FormControl<IHierarchieCnC['id'] | NewHierarchieCnC['id']>;
  nom: FormControl<IHierarchieCnC['nom']>;
  parentHierId: FormControl<IHierarchieCnC['parentHierId']>;
  unitId: FormControl<IHierarchieCnC['unitId']>;
};

export type HierarchieCnCFormGroup = FormGroup<HierarchieCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HierarchieCnCFormService {
  createHierarchieCnCFormGroup(hierarchieCnC: HierarchieCnCFormGroupInput = { id: null }): HierarchieCnCFormGroup {
    const hierarchieCnCRawValue = {
      ...this.getFormDefaults(),
      ...hierarchieCnC,
    };
    return new FormGroup<HierarchieCnCFormGroupContent>({
      id: new FormControl(
        { value: hierarchieCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(hierarchieCnCRawValue.nom, {
        validators: [Validators.required],
      }),
      parentHierId: new FormControl(hierarchieCnCRawValue.parentHierId),
      unitId: new FormControl(hierarchieCnCRawValue.unitId),
    });
  }

  getHierarchieCnC(form: HierarchieCnCFormGroup): IHierarchieCnC | NewHierarchieCnC {
    return form.getRawValue() as IHierarchieCnC | NewHierarchieCnC;
  }

  resetForm(form: HierarchieCnCFormGroup, hierarchieCnC: HierarchieCnCFormGroupInput): void {
    const hierarchieCnCRawValue = { ...this.getFormDefaults(), ...hierarchieCnC };
    form.reset(
      {
        ...hierarchieCnCRawValue,
        id: { value: hierarchieCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HierarchieCnCFormDefaults {
    return {
      id: null,
    };
  }
}
