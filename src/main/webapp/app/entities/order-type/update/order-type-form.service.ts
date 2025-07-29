import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrderType, NewOrderType } from '../order-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrderType for edit and NewOrderTypeFormGroupInput for create.
 */
type OrderTypeFormGroupInput = IOrderType | PartialWithRequiredKeyOf<NewOrderType>;

type OrderTypeFormDefaults = Pick<NewOrderType, 'id'>;

type OrderTypeFormGroupContent = {
  id: FormControl<IOrderType['id'] | NewOrderType['id']>;
  num: FormControl<IOrderType['num']>;
  locRef: FormControl<IOrderType['locRef']>;
  name: FormControl<IOrderType['name']>;
  mstrNum: FormControl<IOrderType['mstrNum']>;
  mstrName: FormControl<IOrderType['mstrName']>;
  catGrpHierName1: FormControl<IOrderType['catGrpHierName1']>;
  catGrpName1: FormControl<IOrderType['catGrpName1']>;
  catGrpHierName2: FormControl<IOrderType['catGrpHierName2']>;
  catGrpName2: FormControl<IOrderType['catGrpName2']>;
  catGrpHierName3: FormControl<IOrderType['catGrpHierName3']>;
  catGrpName3: FormControl<IOrderType['catGrpName3']>;
  catGrpHierName4: FormControl<IOrderType['catGrpHierName4']>;
  catGrpName4: FormControl<IOrderType['catGrpName4']>;
};

export type OrderTypeFormGroup = FormGroup<OrderTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrderTypeFormService {
  createOrderTypeFormGroup(orderType: OrderTypeFormGroupInput = { id: null }): OrderTypeFormGroup {
    const orderTypeRawValue = {
      ...this.getFormDefaults(),
      ...orderType,
    };
    return new FormGroup<OrderTypeFormGroupContent>({
      id: new FormControl(
        { value: orderTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(orderTypeRawValue.num),
      locRef: new FormControl(orderTypeRawValue.locRef, {
        validators: [Validators.required],
      }),
      name: new FormControl(orderTypeRawValue.name),
      mstrNum: new FormControl(orderTypeRawValue.mstrNum),
      mstrName: new FormControl(orderTypeRawValue.mstrName),
      catGrpHierName1: new FormControl(orderTypeRawValue.catGrpHierName1),
      catGrpName1: new FormControl(orderTypeRawValue.catGrpName1),
      catGrpHierName2: new FormControl(orderTypeRawValue.catGrpHierName2),
      catGrpName2: new FormControl(orderTypeRawValue.catGrpName2),
      catGrpHierName3: new FormControl(orderTypeRawValue.catGrpHierName3),
      catGrpName3: new FormControl(orderTypeRawValue.catGrpName3),
      catGrpHierName4: new FormControl(orderTypeRawValue.catGrpHierName4),
      catGrpName4: new FormControl(orderTypeRawValue.catGrpName4),
    });
  }

  getOrderType(form: OrderTypeFormGroup): IOrderType | NewOrderType {
    return form.getRawValue() as IOrderType | NewOrderType;
  }

  resetForm(form: OrderTypeFormGroup, orderType: OrderTypeFormGroupInput): void {
    const orderTypeRawValue = { ...this.getFormDefaults(), ...orderType };
    form.reset(
      {
        ...orderTypeRawValue,
        id: { value: orderTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrderTypeFormDefaults {
    return {
      id: null,
    };
  }
}
