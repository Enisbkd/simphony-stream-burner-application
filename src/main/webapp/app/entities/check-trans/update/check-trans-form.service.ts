import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckTrans, NewCheckTrans } from '../check-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICheckTrans for edit and NewCheckTransFormGroupInput for create.
 */
type CheckTransFormGroupInput = ICheckTrans | PartialWithRequiredKeyOf<NewCheckTrans>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICheckTrans | NewCheckTrans> = Omit<T, 'openTime'> & {
  openTime?: string | null;
};

type CheckTransFormRawValue = FormValueOf<ICheckTrans>;

type NewCheckTransFormRawValue = FormValueOf<NewCheckTrans>;

type CheckTransFormDefaults = Pick<NewCheckTrans, 'id' | 'openTime' | 'isTrainingCheck'>;

type CheckTransFormGroupContent = {
  id: FormControl<CheckTransFormRawValue['id'] | NewCheckTrans['id']>;
  rvcRef: FormControl<CheckTransFormRawValue['rvcRef']>;
  checkRef: FormControl<CheckTransFormRawValue['checkRef']>;
  checkNumber: FormControl<CheckTransFormRawValue['checkNumber']>;
  checkName: FormControl<CheckTransFormRawValue['checkName']>;
  checkEmployeeRef: FormControl<CheckTransFormRawValue['checkEmployeeRef']>;
  orderTypeRef: FormControl<CheckTransFormRawValue['orderTypeRef']>;
  orderChannelRef: FormControl<CheckTransFormRawValue['orderChannelRef']>;
  tableName: FormControl<CheckTransFormRawValue['tableName']>;
  tableGroupNumber: FormControl<CheckTransFormRawValue['tableGroupNumber']>;
  openTime: FormControl<CheckTransFormRawValue['openTime']>;
  guestCount: FormControl<CheckTransFormRawValue['guestCount']>;
  language: FormControl<CheckTransFormRawValue['language']>;
  isTrainingCheck: FormControl<CheckTransFormRawValue['isTrainingCheck']>;
  status: FormControl<CheckTransFormRawValue['status']>;
  preparationStatus: FormControl<CheckTransFormRawValue['preparationStatus']>;
  subtotal: FormControl<CheckTransFormRawValue['subtotal']>;
  subtotalDiscountTotal: FormControl<CheckTransFormRawValue['subtotalDiscountTotal']>;
  autoServiceChargeTotal: FormControl<CheckTransFormRawValue['autoServiceChargeTotal']>;
  serviceChargeTotal: FormControl<CheckTransFormRawValue['serviceChargeTotal']>;
  taxTotal: FormControl<CheckTransFormRawValue['taxTotal']>;
  paymentTotal: FormControl<CheckTransFormRawValue['paymentTotal']>;
  totalDue: FormControl<CheckTransFormRawValue['totalDue']>;
  taxRateId: FormControl<CheckTransFormRawValue['taxRateId']>;
};

export type CheckTransFormGroup = FormGroup<CheckTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CheckTransFormService {
  createCheckTransFormGroup(checkTrans: CheckTransFormGroupInput = { id: null }): CheckTransFormGroup {
    const checkTransRawValue = this.convertCheckTransToCheckTransRawValue({
      ...this.getFormDefaults(),
      ...checkTrans,
    });
    return new FormGroup<CheckTransFormGroupContent>({
      id: new FormControl(
        { value: checkTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      rvcRef: new FormControl(checkTransRawValue.rvcRef),
      checkRef: new FormControl(checkTransRawValue.checkRef),
      checkNumber: new FormControl(checkTransRawValue.checkNumber),
      checkName: new FormControl(checkTransRawValue.checkName),
      checkEmployeeRef: new FormControl(checkTransRawValue.checkEmployeeRef),
      orderTypeRef: new FormControl(checkTransRawValue.orderTypeRef),
      orderChannelRef: new FormControl(checkTransRawValue.orderChannelRef),
      tableName: new FormControl(checkTransRawValue.tableName),
      tableGroupNumber: new FormControl(checkTransRawValue.tableGroupNumber),
      openTime: new FormControl(checkTransRawValue.openTime),
      guestCount: new FormControl(checkTransRawValue.guestCount),
      language: new FormControl(checkTransRawValue.language),
      isTrainingCheck: new FormControl(checkTransRawValue.isTrainingCheck),
      status: new FormControl(checkTransRawValue.status),
      preparationStatus: new FormControl(checkTransRawValue.preparationStatus),
      subtotal: new FormControl(checkTransRawValue.subtotal),
      subtotalDiscountTotal: new FormControl(checkTransRawValue.subtotalDiscountTotal),
      autoServiceChargeTotal: new FormControl(checkTransRawValue.autoServiceChargeTotal),
      serviceChargeTotal: new FormControl(checkTransRawValue.serviceChargeTotal),
      taxTotal: new FormControl(checkTransRawValue.taxTotal),
      paymentTotal: new FormControl(checkTransRawValue.paymentTotal),
      totalDue: new FormControl(checkTransRawValue.totalDue),
      taxRateId: new FormControl(checkTransRawValue.taxRateId),
    });
  }

  getCheckTrans(form: CheckTransFormGroup): ICheckTrans | NewCheckTrans {
    return this.convertCheckTransRawValueToCheckTrans(form.getRawValue() as CheckTransFormRawValue | NewCheckTransFormRawValue);
  }

  resetForm(form: CheckTransFormGroup, checkTrans: CheckTransFormGroupInput): void {
    const checkTransRawValue = this.convertCheckTransToCheckTransRawValue({ ...this.getFormDefaults(), ...checkTrans });
    form.reset(
      {
        ...checkTransRawValue,
        id: { value: checkTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CheckTransFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      openTime: currentTime,
      isTrainingCheck: false,
    };
  }

  private convertCheckTransRawValueToCheckTrans(
    rawCheckTrans: CheckTransFormRawValue | NewCheckTransFormRawValue,
  ): ICheckTrans | NewCheckTrans {
    return {
      ...rawCheckTrans,
      openTime: dayjs(rawCheckTrans.openTime, DATE_TIME_FORMAT),
    };
  }

  private convertCheckTransToCheckTransRawValue(
    checkTrans: ICheckTrans | (Partial<NewCheckTrans> & CheckTransFormDefaults),
  ): CheckTransFormRawValue | PartialWithRequiredKeyOf<NewCheckTransFormRawValue> {
    return {
      ...checkTrans,
      openTime: checkTrans.openTime ? checkTrans.openTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
