import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITaxeRateTrans, NewTaxeRateTrans } from '../taxe-rate-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITaxeRateTrans for edit and NewTaxeRateTransFormGroupInput for create.
 */
type TaxeRateTransFormGroupInput = ITaxeRateTrans | PartialWithRequiredKeyOf<NewTaxeRateTrans>;

type TaxeRateTransFormDefaults = Pick<NewTaxeRateTrans, 'id'>;

type TaxeRateTransFormGroupContent = {
  id: FormControl<ITaxeRateTrans['id'] | NewTaxeRateTrans['id']>;
  orgShortName: FormControl<ITaxeRateTrans['orgShortName']>;
  locRef: FormControl<ITaxeRateTrans['locRef']>;
  rvcRef: FormControl<ITaxeRateTrans['rvcRef']>;
  taxRateId: FormControl<ITaxeRateTrans['taxRateId']>;
  percentage: FormControl<ITaxeRateTrans['percentage']>;
  taxType: FormControl<ITaxeRateTrans['taxType']>;
  nameFR: FormControl<ITaxeRateTrans['nameFR']>;
  nameEN: FormControl<ITaxeRateTrans['nameEN']>;
};

export type TaxeRateTransFormGroup = FormGroup<TaxeRateTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TaxeRateTransFormService {
  createTaxeRateTransFormGroup(taxeRateTrans: TaxeRateTransFormGroupInput = { id: null }): TaxeRateTransFormGroup {
    const taxeRateTransRawValue = {
      ...this.getFormDefaults(),
      ...taxeRateTrans,
    };
    return new FormGroup<TaxeRateTransFormGroupContent>({
      id: new FormControl(
        { value: taxeRateTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      orgShortName: new FormControl(taxeRateTransRawValue.orgShortName),
      locRef: new FormControl(taxeRateTransRawValue.locRef),
      rvcRef: new FormControl(taxeRateTransRawValue.rvcRef),
      taxRateId: new FormControl(taxeRateTransRawValue.taxRateId),
      percentage: new FormControl(taxeRateTransRawValue.percentage),
      taxType: new FormControl(taxeRateTransRawValue.taxType),
      nameFR: new FormControl(taxeRateTransRawValue.nameFR),
      nameEN: new FormControl(taxeRateTransRawValue.nameEN),
    });
  }

  getTaxeRateTrans(form: TaxeRateTransFormGroup): ITaxeRateTrans | NewTaxeRateTrans {
    return form.getRawValue() as ITaxeRateTrans | NewTaxeRateTrans;
  }

  resetForm(form: TaxeRateTransFormGroup, taxeRateTrans: TaxeRateTransFormGroupInput): void {
    const taxeRateTransRawValue = { ...this.getFormDefaults(), ...taxeRateTrans };
    form.reset(
      {
        ...taxeRateTransRawValue,
        id: { value: taxeRateTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TaxeRateTransFormDefaults {
    return {
      id: null,
    };
  }
}
