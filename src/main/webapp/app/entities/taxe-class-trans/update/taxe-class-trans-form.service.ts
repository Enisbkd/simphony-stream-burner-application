import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITaxeClassTrans, NewTaxeClassTrans } from '../taxe-class-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITaxeClassTrans for edit and NewTaxeClassTransFormGroupInput for create.
 */
type TaxeClassTransFormGroupInput = ITaxeClassTrans | PartialWithRequiredKeyOf<NewTaxeClassTrans>;

type TaxeClassTransFormDefaults = Pick<NewTaxeClassTrans, 'id'>;

type TaxeClassTransFormGroupContent = {
  id: FormControl<ITaxeClassTrans['id'] | NewTaxeClassTrans['id']>;
  orgShortName: FormControl<ITaxeClassTrans['orgShortName']>;
  locRef: FormControl<ITaxeClassTrans['locRef']>;
  rvcRef: FormControl<ITaxeClassTrans['rvcRef']>;
  taxClassId: FormControl<ITaxeClassTrans['taxClassId']>;
  activeTaxRateRefs: FormControl<ITaxeClassTrans['activeTaxRateRefs']>;
};

export type TaxeClassTransFormGroup = FormGroup<TaxeClassTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TaxeClassTransFormService {
  createTaxeClassTransFormGroup(taxeClassTrans: TaxeClassTransFormGroupInput = { id: null }): TaxeClassTransFormGroup {
    const taxeClassTransRawValue = {
      ...this.getFormDefaults(),
      ...taxeClassTrans,
    };
    return new FormGroup<TaxeClassTransFormGroupContent>({
      id: new FormControl(
        { value: taxeClassTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      orgShortName: new FormControl(taxeClassTransRawValue.orgShortName),
      locRef: new FormControl(taxeClassTransRawValue.locRef),
      rvcRef: new FormControl(taxeClassTransRawValue.rvcRef),
      taxClassId: new FormControl(taxeClassTransRawValue.taxClassId),
      activeTaxRateRefs: new FormControl(taxeClassTransRawValue.activeTaxRateRefs),
    });
  }

  getTaxeClassTrans(form: TaxeClassTransFormGroup): ITaxeClassTrans | NewTaxeClassTrans {
    return form.getRawValue() as ITaxeClassTrans | NewTaxeClassTrans;
  }

  resetForm(form: TaxeClassTransFormGroup, taxeClassTrans: TaxeClassTransFormGroupInput): void {
    const taxeClassTransRawValue = { ...this.getFormDefaults(), ...taxeClassTrans };
    form.reset(
      {
        ...taxeClassTransRawValue,
        id: { value: taxeClassTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TaxeClassTransFormDefaults {
    return {
      id: null,
    };
  }
}
