import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ILocationCnC, NewLocationCnC } from '../location-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILocationCnC for edit and NewLocationCnCFormGroupInput for create.
 */
type LocationCnCFormGroupInput = ILocationCnC | PartialWithRequiredKeyOf<NewLocationCnC>;

type LocationCnCFormDefaults = Pick<NewLocationCnC, 'id'>;

type LocationCnCFormGroupContent = {
  id: FormControl<ILocationCnC['id'] | NewLocationCnC['id']>;
  hierUnitId: FormControl<ILocationCnC['hierUnitId']>;
  tzIndex: FormControl<ILocationCnC['tzIndex']>;
  tzName: FormControl<ILocationCnC['tzName']>;
  localeInfoId: FormControl<ILocationCnC['localeInfoId']>;
  name: FormControl<ILocationCnC['name']>;
  reportingLocName: FormControl<ILocationCnC['reportingLocName']>;
  locRef: FormControl<ILocationCnC['locRef']>;
  reportingParentEnterpriseLevelName: FormControl<ILocationCnC['reportingParentEnterpriseLevelName']>;
  objectNum: FormControl<ILocationCnC['objectNum']>;
  sbmPmsIfcIp: FormControl<ILocationCnC['sbmPmsIfcIp']>;
  sbmPmsIfcPort: FormControl<ILocationCnC['sbmPmsIfcPort']>;
  sbmPriveRoomStart: FormControl<ILocationCnC['sbmPriveRoomStart']>;
  sbmPriveRoomEnd: FormControl<ILocationCnC['sbmPriveRoomEnd']>;
  sbmPmsSendAllDetails: FormControl<ILocationCnC['sbmPmsSendAllDetails']>;
  sbmPmsSendFullDscv: FormControl<ILocationCnC['sbmPmsSendFullDscv']>;
  sbmPmsSend64Tax: FormControl<ILocationCnC['sbmPmsSend64Tax']>;
  sbmCardPaymentUrl: FormControl<ILocationCnC['sbmCardPaymentUrl']>;
  sbmCheckHotelDataUrl: FormControl<ILocationCnC['sbmCheckHotelDataUrl']>;
  sbmVoucherSvcUrl: FormControl<ILocationCnC['sbmVoucherSvcUrl']>;
  sbmVoucherInvPm: FormControl<ILocationCnC['sbmVoucherInvPm']>;
  sbmVoucherCorpPm: FormControl<ILocationCnC['sbmVoucherCorpPm']>;
  sbmVoucherRewardPm: FormControl<ILocationCnC['sbmVoucherRewardPm']>;
  sbmVoucherMcPm: FormControl<ILocationCnC['sbmVoucherMcPm']>;
  sbmPmsIfcPort2: FormControl<ILocationCnC['sbmPmsIfcPort2']>;
  sbmPmsIfcPort3: FormControl<ILocationCnC['sbmPmsIfcPort3']>;
  sbmPmsIfcPort4: FormControl<ILocationCnC['sbmPmsIfcPort4']>;
  sbmTimeout: FormControl<ILocationCnC['sbmTimeout']>;
};

export type LocationCnCFormGroup = FormGroup<LocationCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LocationCnCFormService {
  createLocationCnCFormGroup(locationCnC: LocationCnCFormGroupInput = { id: null }): LocationCnCFormGroup {
    const locationCnCRawValue = {
      ...this.getFormDefaults(),
      ...locationCnC,
    };
    return new FormGroup<LocationCnCFormGroupContent>({
      id: new FormControl(
        { value: locationCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      hierUnitId: new FormControl(locationCnCRawValue.hierUnitId),
      tzIndex: new FormControl(locationCnCRawValue.tzIndex),
      tzName: new FormControl(locationCnCRawValue.tzName),
      localeInfoId: new FormControl(locationCnCRawValue.localeInfoId),
      name: new FormControl(locationCnCRawValue.name),
      reportingLocName: new FormControl(locationCnCRawValue.reportingLocName),
      locRef: new FormControl(locationCnCRawValue.locRef),
      reportingParentEnterpriseLevelName: new FormControl(locationCnCRawValue.reportingParentEnterpriseLevelName),
      objectNum: new FormControl(locationCnCRawValue.objectNum),
      sbmPmsIfcIp: new FormControl(locationCnCRawValue.sbmPmsIfcIp),
      sbmPmsIfcPort: new FormControl(locationCnCRawValue.sbmPmsIfcPort),
      sbmPriveRoomStart: new FormControl(locationCnCRawValue.sbmPriveRoomStart),
      sbmPriveRoomEnd: new FormControl(locationCnCRawValue.sbmPriveRoomEnd),
      sbmPmsSendAllDetails: new FormControl(locationCnCRawValue.sbmPmsSendAllDetails),
      sbmPmsSendFullDscv: new FormControl(locationCnCRawValue.sbmPmsSendFullDscv),
      sbmPmsSend64Tax: new FormControl(locationCnCRawValue.sbmPmsSend64Tax),
      sbmCardPaymentUrl: new FormControl(locationCnCRawValue.sbmCardPaymentUrl),
      sbmCheckHotelDataUrl: new FormControl(locationCnCRawValue.sbmCheckHotelDataUrl),
      sbmVoucherSvcUrl: new FormControl(locationCnCRawValue.sbmVoucherSvcUrl),
      sbmVoucherInvPm: new FormControl(locationCnCRawValue.sbmVoucherInvPm),
      sbmVoucherCorpPm: new FormControl(locationCnCRawValue.sbmVoucherCorpPm),
      sbmVoucherRewardPm: new FormControl(locationCnCRawValue.sbmVoucherRewardPm),
      sbmVoucherMcPm: new FormControl(locationCnCRawValue.sbmVoucherMcPm),
      sbmPmsIfcPort2: new FormControl(locationCnCRawValue.sbmPmsIfcPort2),
      sbmPmsIfcPort3: new FormControl(locationCnCRawValue.sbmPmsIfcPort3),
      sbmPmsIfcPort4: new FormControl(locationCnCRawValue.sbmPmsIfcPort4),
      sbmTimeout: new FormControl(locationCnCRawValue.sbmTimeout),
    });
  }

  getLocationCnC(form: LocationCnCFormGroup): ILocationCnC | NewLocationCnC {
    return form.getRawValue() as ILocationCnC | NewLocationCnC;
  }

  resetForm(form: LocationCnCFormGroup, locationCnC: LocationCnCFormGroupInput): void {
    const locationCnCRawValue = { ...this.getFormDefaults(), ...locationCnC };
    form.reset(
      {
        ...locationCnCRawValue,
        id: { value: locationCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LocationCnCFormDefaults {
    return {
      id: null,
    };
  }
}
