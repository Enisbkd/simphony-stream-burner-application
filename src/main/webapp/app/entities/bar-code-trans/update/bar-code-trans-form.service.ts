import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IBarCodeTrans, NewBarCodeTrans } from '../bar-code-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBarCodeTrans for edit and NewBarCodeTransFormGroupInput for create.
 */
type BarCodeTransFormGroupInput = IBarCodeTrans | PartialWithRequiredKeyOf<NewBarCodeTrans>;

type BarCodeTransFormDefaults = Pick<NewBarCodeTrans, 'id'>;

type BarCodeTransFormGroupContent = {
  id: FormControl<IBarCodeTrans['id'] | NewBarCodeTrans['id']>;
  locRef: FormControl<IBarCodeTrans['locRef']>;
  rvcRef: FormControl<IBarCodeTrans['rvcRef']>;
  barcodeId: FormControl<IBarCodeTrans['barcodeId']>;
  barcode: FormControl<IBarCodeTrans['barcode']>;
  menuItemId: FormControl<IBarCodeTrans['menuItemId']>;
  defenitionSequence: FormControl<IBarCodeTrans['defenitionSequence']>;
  price: FormControl<IBarCodeTrans['price']>;
  priceSequence: FormControl<IBarCodeTrans['priceSequence']>;
  preparationCost: FormControl<IBarCodeTrans['preparationCost']>;
};

export type BarCodeTransFormGroup = FormGroup<BarCodeTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BarCodeTransFormService {
  createBarCodeTransFormGroup(barCodeTrans: BarCodeTransFormGroupInput = { id: null }): BarCodeTransFormGroup {
    const barCodeTransRawValue = {
      ...this.getFormDefaults(),
      ...barCodeTrans,
    };
    return new FormGroup<BarCodeTransFormGroupContent>({
      id: new FormControl(
        { value: barCodeTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      locRef: new FormControl(barCodeTransRawValue.locRef),
      rvcRef: new FormControl(barCodeTransRawValue.rvcRef),
      barcodeId: new FormControl(barCodeTransRawValue.barcodeId),
      barcode: new FormControl(barCodeTransRawValue.barcode),
      menuItemId: new FormControl(barCodeTransRawValue.menuItemId),
      defenitionSequence: new FormControl(barCodeTransRawValue.defenitionSequence),
      price: new FormControl(barCodeTransRawValue.price),
      priceSequence: new FormControl(barCodeTransRawValue.priceSequence),
      preparationCost: new FormControl(barCodeTransRawValue.preparationCost),
    });
  }

  getBarCodeTrans(form: BarCodeTransFormGroup): IBarCodeTrans | NewBarCodeTrans {
    return form.getRawValue() as IBarCodeTrans | NewBarCodeTrans;
  }

  resetForm(form: BarCodeTransFormGroup, barCodeTrans: BarCodeTransFormGroupInput): void {
    const barCodeTransRawValue = { ...this.getFormDefaults(), ...barCodeTrans };
    form.reset(
      {
        ...barCodeTransRawValue,
        id: { value: barCodeTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BarCodeTransFormDefaults {
    return {
      id: null,
    };
  }
}
