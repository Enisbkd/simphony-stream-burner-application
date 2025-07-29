import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrderChannelBI, NewOrderChannelBI } from '../order-channel-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrderChannelBI for edit and NewOrderChannelBIFormGroupInput for create.
 */
type OrderChannelBIFormGroupInput = IOrderChannelBI | PartialWithRequiredKeyOf<NewOrderChannelBI>;

type OrderChannelBIFormDefaults = Pick<NewOrderChannelBI, 'id'>;

type OrderChannelBIFormGroupContent = {
  id: FormControl<IOrderChannelBI['id'] | NewOrderChannelBI['id']>;
  num: FormControl<IOrderChannelBI['num']>;
  locRef: FormControl<IOrderChannelBI['locRef']>;
  name: FormControl<IOrderChannelBI['name']>;
  mstrNum: FormControl<IOrderChannelBI['mstrNum']>;
  mstrName: FormControl<IOrderChannelBI['mstrName']>;
};

export type OrderChannelBIFormGroup = FormGroup<OrderChannelBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrderChannelBIFormService {
  createOrderChannelBIFormGroup(orderChannelBI: OrderChannelBIFormGroupInput = { id: null }): OrderChannelBIFormGroup {
    const orderChannelBIRawValue = {
      ...this.getFormDefaults(),
      ...orderChannelBI,
    };
    return new FormGroup<OrderChannelBIFormGroupContent>({
      id: new FormControl(
        { value: orderChannelBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(orderChannelBIRawValue.num),
      locRef: new FormControl(orderChannelBIRawValue.locRef, {
        validators: [Validators.required],
      }),
      name: new FormControl(orderChannelBIRawValue.name),
      mstrNum: new FormControl(orderChannelBIRawValue.mstrNum),
      mstrName: new FormControl(orderChannelBIRawValue.mstrName),
    });
  }

  getOrderChannelBI(form: OrderChannelBIFormGroup): IOrderChannelBI | NewOrderChannelBI {
    return form.getRawValue() as IOrderChannelBI | NewOrderChannelBI;
  }

  resetForm(form: OrderChannelBIFormGroup, orderChannelBI: OrderChannelBIFormGroupInput): void {
    const orderChannelBIRawValue = { ...this.getFormDefaults(), ...orderChannelBI };
    form.reset(
      {
        ...orderChannelBIRawValue,
        id: { value: orderChannelBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrderChannelBIFormDefaults {
    return {
      id: null,
    };
  }
}
