import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITaxeBI, NewTaxeBI } from '../taxe-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITaxeBI for edit and NewTaxeBIFormGroupInput for create.
 */
type TaxeBIFormGroupInput = ITaxeBI | PartialWithRequiredKeyOf<NewTaxeBI>;

type TaxeBIFormDefaults = Pick<NewTaxeBI, 'id'>;

type TaxeBIFormGroupContent = {
  id: FormControl<ITaxeBI['id'] | NewTaxeBI['id']>;
  locRef: FormControl<ITaxeBI['locRef']>;
  num: FormControl<ITaxeBI['num']>;
  name: FormControl<ITaxeBI['name']>;
  type: FormControl<ITaxeBI['type']>;
};

export type TaxeBIFormGroup = FormGroup<TaxeBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TaxeBIFormService {
  createTaxeBIFormGroup(taxeBI: TaxeBIFormGroupInput = { id: null }): TaxeBIFormGroup {
    const taxeBIRawValue = {
      ...this.getFormDefaults(),
      ...taxeBI,
    };
    return new FormGroup<TaxeBIFormGroupContent>({
      id: new FormControl(
        { value: taxeBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      locRef: new FormControl(taxeBIRawValue.locRef),
      num: new FormControl(taxeBIRawValue.num),
      name: new FormControl(taxeBIRawValue.name),
      type: new FormControl(taxeBIRawValue.type),
    });
  }

  getTaxeBI(form: TaxeBIFormGroup): ITaxeBI | NewTaxeBI {
    return form.getRawValue() as ITaxeBI | NewTaxeBI;
  }

  resetForm(form: TaxeBIFormGroup, taxeBI: TaxeBIFormGroupInput): void {
    const taxeBIRawValue = { ...this.getFormDefaults(), ...taxeBI };
    form.reset(
      {
        ...taxeBIRawValue,
        id: { value: taxeBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TaxeBIFormDefaults {
    return {
      id: null,
    };
  }
}
