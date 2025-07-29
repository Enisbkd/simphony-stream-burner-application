import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrderChannel, NewOrderChannel } from '../order-channel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrderChannel for edit and NewOrderChannelFormGroupInput for create.
 */
type OrderChannelFormGroupInput = IOrderChannel | PartialWithRequiredKeyOf<NewOrderChannel>;

type OrderChannelFormDefaults = Pick<NewOrderChannel, 'id'>;

type OrderChannelFormGroupContent = {
  id: FormControl<IOrderChannel['id'] | NewOrderChannel['id']>;
  num: FormControl<IOrderChannel['num']>;
  locRef: FormControl<IOrderChannel['locRef']>;
  name: FormControl<IOrderChannel['name']>;
  mstrNum: FormControl<IOrderChannel['mstrNum']>;
  mstrName: FormControl<IOrderChannel['mstrName']>;
};

export type OrderChannelFormGroup = FormGroup<OrderChannelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrderChannelFormService {
  createOrderChannelFormGroup(orderChannel: OrderChannelFormGroupInput = { id: null }): OrderChannelFormGroup {
    const orderChannelRawValue = {
      ...this.getFormDefaults(),
      ...orderChannel,
    };
    return new FormGroup<OrderChannelFormGroupContent>({
      id: new FormControl(
        { value: orderChannelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(orderChannelRawValue.num),
      locRef: new FormControl(orderChannelRawValue.locRef, {
        validators: [Validators.required],
      }),
      name: new FormControl(orderChannelRawValue.name),
      mstrNum: new FormControl(orderChannelRawValue.mstrNum),
      mstrName: new FormControl(orderChannelRawValue.mstrName),
    });
  }

  getOrderChannel(form: OrderChannelFormGroup): IOrderChannel | NewOrderChannel {
    return form.getRawValue() as IOrderChannel | NewOrderChannel;
  }

  resetForm(form: OrderChannelFormGroup, orderChannel: OrderChannelFormGroupInput): void {
    const orderChannelRawValue = { ...this.getFormDefaults(), ...orderChannel };
    form.reset(
      {
        ...orderChannelRawValue,
        id: { value: orderChannelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrderChannelFormDefaults {
    return {
      id: null,
    };
  }
}
