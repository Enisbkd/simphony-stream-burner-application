import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGuestCheckBI, NewGuestCheckBI } from '../guest-check-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGuestCheckBI for edit and NewGuestCheckBIFormGroupInput for create.
 */
type GuestCheckBIFormGroupInput = IGuestCheckBI | PartialWithRequiredKeyOf<NewGuestCheckBI>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGuestCheckBI | NewGuestCheckBI> = Omit<T, 'opnLcl' | 'clsdLcl'> & {
  opnLcl?: string | null;
  clsdLcl?: string | null;
};

type GuestCheckBIFormRawValue = FormValueOf<IGuestCheckBI>;

type NewGuestCheckBIFormRawValue = FormValueOf<NewGuestCheckBI>;

type GuestCheckBIFormDefaults = Pick<NewGuestCheckBI, 'id' | 'opnLcl' | 'clsdLcl' | 'cancelFlag'>;

type GuestCheckBIFormGroupContent = {
  id: FormControl<GuestCheckBIFormRawValue['id'] | NewGuestCheckBI['id']>;
  guestCheckId: FormControl<GuestCheckBIFormRawValue['guestCheckId']>;
  chkNum: FormControl<GuestCheckBIFormRawValue['chkNum']>;
  opnLcl: FormControl<GuestCheckBIFormRawValue['opnLcl']>;
  clsdLcl: FormControl<GuestCheckBIFormRawValue['clsdLcl']>;
  cancelFlag: FormControl<GuestCheckBIFormRawValue['cancelFlag']>;
  gstCnt: FormControl<GuestCheckBIFormRawValue['gstCnt']>;
  tblNum: FormControl<GuestCheckBIFormRawValue['tblNum']>;
  taxCollTtl: FormControl<GuestCheckBIFormRawValue['taxCollTtl']>;
  subTtl: FormControl<GuestCheckBIFormRawValue['subTtl']>;
  chkTtl: FormControl<GuestCheckBIFormRawValue['chkTtl']>;
  svcChgTtl: FormControl<GuestCheckBIFormRawValue['svcChgTtl']>;
  tipTotal: FormControl<GuestCheckBIFormRawValue['tipTotal']>;
  dscTtl: FormControl<GuestCheckBIFormRawValue['dscTtl']>;
  errorCorrectTtl: FormControl<GuestCheckBIFormRawValue['errorCorrectTtl']>;
  returnTtl: FormControl<GuestCheckBIFormRawValue['returnTtl']>;
  xferToChkNum: FormControl<GuestCheckBIFormRawValue['xferToChkNum']>;
  xferStatus: FormControl<GuestCheckBIFormRawValue['xferStatus']>;
  otNum: FormControl<GuestCheckBIFormRawValue['otNum']>;
  locRef: FormControl<GuestCheckBIFormRawValue['locRef']>;
};

export type GuestCheckBIFormGroup = FormGroup<GuestCheckBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestCheckBIFormService {
  createGuestCheckBIFormGroup(guestCheckBI: GuestCheckBIFormGroupInput = { id: null }): GuestCheckBIFormGroup {
    const guestCheckBIRawValue = this.convertGuestCheckBIToGuestCheckBIRawValue({
      ...this.getFormDefaults(),
      ...guestCheckBI,
    });
    return new FormGroup<GuestCheckBIFormGroupContent>({
      id: new FormControl(
        { value: guestCheckBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      guestCheckId: new FormControl(guestCheckBIRawValue.guestCheckId),
      chkNum: new FormControl(guestCheckBIRawValue.chkNum),
      opnLcl: new FormControl(guestCheckBIRawValue.opnLcl),
      clsdLcl: new FormControl(guestCheckBIRawValue.clsdLcl),
      cancelFlag: new FormControl(guestCheckBIRawValue.cancelFlag),
      gstCnt: new FormControl(guestCheckBIRawValue.gstCnt),
      tblNum: new FormControl(guestCheckBIRawValue.tblNum),
      taxCollTtl: new FormControl(guestCheckBIRawValue.taxCollTtl),
      subTtl: new FormControl(guestCheckBIRawValue.subTtl),
      chkTtl: new FormControl(guestCheckBIRawValue.chkTtl),
      svcChgTtl: new FormControl(guestCheckBIRawValue.svcChgTtl),
      tipTotal: new FormControl(guestCheckBIRawValue.tipTotal),
      dscTtl: new FormControl(guestCheckBIRawValue.dscTtl),
      errorCorrectTtl: new FormControl(guestCheckBIRawValue.errorCorrectTtl),
      returnTtl: new FormControl(guestCheckBIRawValue.returnTtl),
      xferToChkNum: new FormControl(guestCheckBIRawValue.xferToChkNum),
      xferStatus: new FormControl(guestCheckBIRawValue.xferStatus),
      otNum: new FormControl(guestCheckBIRawValue.otNum),
      locRef: new FormControl(guestCheckBIRawValue.locRef),
    });
  }

  getGuestCheckBI(form: GuestCheckBIFormGroup): IGuestCheckBI | NewGuestCheckBI {
    return this.convertGuestCheckBIRawValueToGuestCheckBI(form.getRawValue() as GuestCheckBIFormRawValue | NewGuestCheckBIFormRawValue);
  }

  resetForm(form: GuestCheckBIFormGroup, guestCheckBI: GuestCheckBIFormGroupInput): void {
    const guestCheckBIRawValue = this.convertGuestCheckBIToGuestCheckBIRawValue({ ...this.getFormDefaults(), ...guestCheckBI });
    form.reset(
      {
        ...guestCheckBIRawValue,
        id: { value: guestCheckBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GuestCheckBIFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      opnLcl: currentTime,
      clsdLcl: currentTime,
      cancelFlag: false,
    };
  }

  private convertGuestCheckBIRawValueToGuestCheckBI(
    rawGuestCheckBI: GuestCheckBIFormRawValue | NewGuestCheckBIFormRawValue,
  ): IGuestCheckBI | NewGuestCheckBI {
    return {
      ...rawGuestCheckBI,
      opnLcl: dayjs(rawGuestCheckBI.opnLcl, DATE_TIME_FORMAT),
      clsdLcl: dayjs(rawGuestCheckBI.clsdLcl, DATE_TIME_FORMAT),
    };
  }

  private convertGuestCheckBIToGuestCheckBIRawValue(
    guestCheckBI: IGuestCheckBI | (Partial<NewGuestCheckBI> & GuestCheckBIFormDefaults),
  ): GuestCheckBIFormRawValue | PartialWithRequiredKeyOf<NewGuestCheckBIFormRawValue> {
    return {
      ...guestCheckBI,
      opnLcl: guestCheckBI.opnLcl ? guestCheckBI.opnLcl.format(DATE_TIME_FORMAT) : undefined,
      clsdLcl: guestCheckBI.clsdLcl ? guestCheckBI.clsdLcl.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
