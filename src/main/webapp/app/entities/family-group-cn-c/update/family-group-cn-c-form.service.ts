import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFamilyGroupCnC, NewFamilyGroupCnC } from '../family-group-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFamilyGroupCnC for edit and NewFamilyGroupCnCFormGroupInput for create.
 */
type FamilyGroupCnCFormGroupInput = IFamilyGroupCnC | PartialWithRequiredKeyOf<NewFamilyGroupCnC>;

type FamilyGroupCnCFormDefaults = Pick<NewFamilyGroupCnC, 'id'>;

type FamilyGroupCnCFormGroupContent = {
  id: FormControl<IFamilyGroupCnC['id'] | NewFamilyGroupCnC['id']>;
  nom: FormControl<IFamilyGroupCnC['nom']>;
  nomCourt: FormControl<IFamilyGroupCnC['nomCourt']>;
  majorGroupRef: FormControl<IFamilyGroupCnC['majorGroupRef']>;
};

export type FamilyGroupCnCFormGroup = FormGroup<FamilyGroupCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FamilyGroupCnCFormService {
  createFamilyGroupCnCFormGroup(familyGroupCnC: FamilyGroupCnCFormGroupInput = { id: null }): FamilyGroupCnCFormGroup {
    const familyGroupCnCRawValue = {
      ...this.getFormDefaults(),
      ...familyGroupCnC,
    };
    return new FormGroup<FamilyGroupCnCFormGroupContent>({
      id: new FormControl(
        { value: familyGroupCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(familyGroupCnCRawValue.nom),
      nomCourt: new FormControl(familyGroupCnCRawValue.nomCourt),
      majorGroupRef: new FormControl(familyGroupCnCRawValue.majorGroupRef),
    });
  }

  getFamilyGroupCnC(form: FamilyGroupCnCFormGroup): IFamilyGroupCnC | NewFamilyGroupCnC {
    return form.getRawValue() as IFamilyGroupCnC | NewFamilyGroupCnC;
  }

  resetForm(form: FamilyGroupCnCFormGroup, familyGroupCnC: FamilyGroupCnCFormGroupInput): void {
    const familyGroupCnCRawValue = { ...this.getFormDefaults(), ...familyGroupCnC };
    form.reset(
      {
        ...familyGroupCnCRawValue,
        id: { value: familyGroupCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FamilyGroupCnCFormDefaults {
    return {
      id: null,
    };
  }
}
