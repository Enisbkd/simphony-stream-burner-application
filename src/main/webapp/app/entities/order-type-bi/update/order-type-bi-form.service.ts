import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrderTypeBI, NewOrderTypeBI } from '../order-type-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrderTypeBI for edit and NewOrderTypeBIFormGroupInput for create.
 */
type OrderTypeBIFormGroupInput = IOrderTypeBI | PartialWithRequiredKeyOf<NewOrderTypeBI>;

type OrderTypeBIFormDefaults = Pick<NewOrderTypeBI, 'id'>;

type OrderTypeBIFormGroupContent = {
  id: FormControl<IOrderTypeBI['id'] | NewOrderTypeBI['id']>;
  num: FormControl<IOrderTypeBI['num']>;
  locRef: FormControl<IOrderTypeBI['locRef']>;
  name: FormControl<IOrderTypeBI['name']>;
  mstrNum: FormControl<IOrderTypeBI['mstrNum']>;
  mstrName: FormControl<IOrderTypeBI['mstrName']>;
  catGrpHierName1: FormControl<IOrderTypeBI['catGrpHierName1']>;
  catGrpName1: FormControl<IOrderTypeBI['catGrpName1']>;
  catGrpHierName2: FormControl<IOrderTypeBI['catGrpHierName2']>;
  catGrpName2: FormControl<IOrderTypeBI['catGrpName2']>;
  catGrpHierName3: FormControl<IOrderTypeBI['catGrpHierName3']>;
  catGrpName3: FormControl<IOrderTypeBI['catGrpName3']>;
  catGrpHierName4: FormControl<IOrderTypeBI['catGrpHierName4']>;
  catGrpName4: FormControl<IOrderTypeBI['catGrpName4']>;
};

export type OrderTypeBIFormGroup = FormGroup<OrderTypeBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrderTypeBIFormService {
  createOrderTypeBIFormGroup(orderTypeBI: OrderTypeBIFormGroupInput = { id: null }): OrderTypeBIFormGroup {
    const orderTypeBIRawValue = {
      ...this.getFormDefaults(),
      ...orderTypeBI,
    };
    return new FormGroup<OrderTypeBIFormGroupContent>({
      id: new FormControl(
        { value: orderTypeBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(orderTypeBIRawValue.num),
      locRef: new FormControl(orderTypeBIRawValue.locRef, {
        validators: [Validators.required],
      }),
      name: new FormControl(orderTypeBIRawValue.name),
      mstrNum: new FormControl(orderTypeBIRawValue.mstrNum),
      mstrName: new FormControl(orderTypeBIRawValue.mstrName),
      catGrpHierName1: new FormControl(orderTypeBIRawValue.catGrpHierName1),
      catGrpName1: new FormControl(orderTypeBIRawValue.catGrpName1),
      catGrpHierName2: new FormControl(orderTypeBIRawValue.catGrpHierName2),
      catGrpName2: new FormControl(orderTypeBIRawValue.catGrpName2),
      catGrpHierName3: new FormControl(orderTypeBIRawValue.catGrpHierName3),
      catGrpName3: new FormControl(orderTypeBIRawValue.catGrpName3),
      catGrpHierName4: new FormControl(orderTypeBIRawValue.catGrpHierName4),
      catGrpName4: new FormControl(orderTypeBIRawValue.catGrpName4),
    });
  }

  getOrderTypeBI(form: OrderTypeBIFormGroup): IOrderTypeBI | NewOrderTypeBI {
    return form.getRawValue() as IOrderTypeBI | NewOrderTypeBI;
  }

  resetForm(form: OrderTypeBIFormGroup, orderTypeBI: OrderTypeBIFormGroupInput): void {
    const orderTypeBIRawValue = { ...this.getFormDefaults(), ...orderTypeBI };
    form.reset(
      {
        ...orderTypeBIRawValue,
        id: { value: orderTypeBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrderTypeBIFormDefaults {
    return {
      id: null,
    };
  }
}
