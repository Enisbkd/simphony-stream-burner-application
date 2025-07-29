import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDetailLineBI, NewDetailLineBI } from '../detail-line-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetailLineBI for edit and NewDetailLineBIFormGroupInput for create.
 */
type DetailLineBIFormGroupInput = IDetailLineBI | PartialWithRequiredKeyOf<NewDetailLineBI>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDetailLineBI | NewDetailLineBI> = Omit<T, 'detailUTC' | 'detailLcl'> & {
  detailUTC?: string | null;
  detailLcl?: string | null;
};

type DetailLineBIFormRawValue = FormValueOf<IDetailLineBI>;

type NewDetailLineBIFormRawValue = FormValueOf<NewDetailLineBI>;

type DetailLineBIFormDefaults = Pick<
  NewDetailLineBI,
  'id' | 'detailUTC' | 'detailLcl' | 'errCorFlag' | 'vdFlag' | 'returnFlag' | 'doNotShowFlag'
>;

type DetailLineBIFormGroupContent = {
  id: FormControl<DetailLineBIFormRawValue['id'] | NewDetailLineBI['id']>;
  guestCheckLineItemId: FormControl<DetailLineBIFormRawValue['guestCheckLineItemId']>;
  detailUTC: FormControl<DetailLineBIFormRawValue['detailUTC']>;
  detailLcl: FormControl<DetailLineBIFormRawValue['detailLcl']>;
  seatNum: FormControl<DetailLineBIFormRawValue['seatNum']>;
  prcLvl: FormControl<DetailLineBIFormRawValue['prcLvl']>;
  dspTtl: FormControl<DetailLineBIFormRawValue['dspTtl']>;
  dspQty: FormControl<DetailLineBIFormRawValue['dspQty']>;
  errCorFlag: FormControl<DetailLineBIFormRawValue['errCorFlag']>;
  vdFlag: FormControl<DetailLineBIFormRawValue['vdFlag']>;
  returnFlag: FormControl<DetailLineBIFormRawValue['returnFlag']>;
  doNotShowFlag: FormControl<DetailLineBIFormRawValue['doNotShowFlag']>;
  aggTtl: FormControl<DetailLineBIFormRawValue['aggTtl']>;
  rsnCodeNum: FormControl<DetailLineBIFormRawValue['rsnCodeNum']>;
  refInfo1: FormControl<DetailLineBIFormRawValue['refInfo1']>;
  refInfo2: FormControl<DetailLineBIFormRawValue['refInfo2']>;
  svcRndNum: FormControl<DetailLineBIFormRawValue['svcRndNum']>;
  parDtlId: FormControl<DetailLineBIFormRawValue['parDtlId']>;
  chkEmpId: FormControl<DetailLineBIFormRawValue['chkEmpId']>;
  transEmpNum: FormControl<DetailLineBIFormRawValue['transEmpNum']>;
  mgrEmpNum: FormControl<DetailLineBIFormRawValue['mgrEmpNum']>;
  mealEmpNum: FormControl<DetailLineBIFormRawValue['mealEmpNum']>;
  dscNum: FormControl<DetailLineBIFormRawValue['dscNum']>;
  dscMiNum: FormControl<DetailLineBIFormRawValue['dscMiNum']>;
  svcChgNum: FormControl<DetailLineBIFormRawValue['svcChgNum']>;
  tmedNum: FormControl<DetailLineBIFormRawValue['tmedNum']>;
  miNum: FormControl<DetailLineBIFormRawValue['miNum']>;
  guestCheckBI: FormControl<DetailLineBIFormRawValue['guestCheckBI']>;
};

export type DetailLineBIFormGroup = FormGroup<DetailLineBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetailLineBIFormService {
  createDetailLineBIFormGroup(detailLineBI: DetailLineBIFormGroupInput = { id: null }): DetailLineBIFormGroup {
    const detailLineBIRawValue = this.convertDetailLineBIToDetailLineBIRawValue({
      ...this.getFormDefaults(),
      ...detailLineBI,
    });
    return new FormGroup<DetailLineBIFormGroupContent>({
      id: new FormControl(
        { value: detailLineBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      guestCheckLineItemId: new FormControl(detailLineBIRawValue.guestCheckLineItemId),
      detailUTC: new FormControl(detailLineBIRawValue.detailUTC),
      detailLcl: new FormControl(detailLineBIRawValue.detailLcl),
      seatNum: new FormControl(detailLineBIRawValue.seatNum),
      prcLvl: new FormControl(detailLineBIRawValue.prcLvl),
      dspTtl: new FormControl(detailLineBIRawValue.dspTtl),
      dspQty: new FormControl(detailLineBIRawValue.dspQty),
      errCorFlag: new FormControl(detailLineBIRawValue.errCorFlag),
      vdFlag: new FormControl(detailLineBIRawValue.vdFlag),
      returnFlag: new FormControl(detailLineBIRawValue.returnFlag),
      doNotShowFlag: new FormControl(detailLineBIRawValue.doNotShowFlag),
      aggTtl: new FormControl(detailLineBIRawValue.aggTtl),
      rsnCodeNum: new FormControl(detailLineBIRawValue.rsnCodeNum),
      refInfo1: new FormControl(detailLineBIRawValue.refInfo1),
      refInfo2: new FormControl(detailLineBIRawValue.refInfo2),
      svcRndNum: new FormControl(detailLineBIRawValue.svcRndNum),
      parDtlId: new FormControl(detailLineBIRawValue.parDtlId),
      chkEmpId: new FormControl(detailLineBIRawValue.chkEmpId),
      transEmpNum: new FormControl(detailLineBIRawValue.transEmpNum),
      mgrEmpNum: new FormControl(detailLineBIRawValue.mgrEmpNum),
      mealEmpNum: new FormControl(detailLineBIRawValue.mealEmpNum),
      dscNum: new FormControl(detailLineBIRawValue.dscNum),
      dscMiNum: new FormControl(detailLineBIRawValue.dscMiNum),
      svcChgNum: new FormControl(detailLineBIRawValue.svcChgNum),
      tmedNum: new FormControl(detailLineBIRawValue.tmedNum),
      miNum: new FormControl(detailLineBIRawValue.miNum),
      guestCheckBI: new FormControl(detailLineBIRawValue.guestCheckBI),
    });
  }

  getDetailLineBI(form: DetailLineBIFormGroup): IDetailLineBI | NewDetailLineBI {
    return this.convertDetailLineBIRawValueToDetailLineBI(form.getRawValue() as DetailLineBIFormRawValue | NewDetailLineBIFormRawValue);
  }

  resetForm(form: DetailLineBIFormGroup, detailLineBI: DetailLineBIFormGroupInput): void {
    const detailLineBIRawValue = this.convertDetailLineBIToDetailLineBIRawValue({ ...this.getFormDefaults(), ...detailLineBI });
    form.reset(
      {
        ...detailLineBIRawValue,
        id: { value: detailLineBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DetailLineBIFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      detailUTC: currentTime,
      detailLcl: currentTime,
      errCorFlag: false,
      vdFlag: false,
      returnFlag: false,
      doNotShowFlag: false,
    };
  }

  private convertDetailLineBIRawValueToDetailLineBI(
    rawDetailLineBI: DetailLineBIFormRawValue | NewDetailLineBIFormRawValue,
  ): IDetailLineBI | NewDetailLineBI {
    return {
      ...rawDetailLineBI,
      detailUTC: dayjs(rawDetailLineBI.detailUTC, DATE_TIME_FORMAT),
      detailLcl: dayjs(rawDetailLineBI.detailLcl, DATE_TIME_FORMAT),
    };
  }

  private convertDetailLineBIToDetailLineBIRawValue(
    detailLineBI: IDetailLineBI | (Partial<NewDetailLineBI> & DetailLineBIFormDefaults),
  ): DetailLineBIFormRawValue | PartialWithRequiredKeyOf<NewDetailLineBIFormRawValue> {
    return {
      ...detailLineBI,
      detailUTC: detailLineBI.detailUTC ? detailLineBI.detailUTC.format(DATE_TIME_FORMAT) : undefined,
      detailLcl: detailLineBI.detailLcl ? detailLineBI.detailLcl.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
