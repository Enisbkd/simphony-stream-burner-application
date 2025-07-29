import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ILocation, NewLocation } from '../location.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILocation for edit and NewLocationFormGroupInput for create.
 */
type LocationFormGroupInput = ILocation | PartialWithRequiredKeyOf<NewLocation>;

type LocationFormDefaults = Pick<NewLocation, 'id'>;

type LocationFormGroupContent = {
  id: FormControl<ILocation['id'] | NewLocation['id']>;
  hierUnitId: FormControl<ILocation['hierUnitId']>;
  tzIndex: FormControl<ILocation['tzIndex']>;
  tzName: FormControl<ILocation['tzName']>;
  localeInfoId: FormControl<ILocation['localeInfoId']>;
  name: FormControl<ILocation['name']>;
  reportingLocName: FormControl<ILocation['reportingLocName']>;
  locRef: FormControl<ILocation['locRef']>;
  reportingParentEnterpriseLevelName: FormControl<ILocation['reportingParentEnterpriseLevelName']>;
  objectNum: FormControl<ILocation['objectNum']>;
  sbmPmsIfcIp: FormControl<ILocation['sbmPmsIfcIp']>;
  sbmPmsIfcPort: FormControl<ILocation['sbmPmsIfcPort']>;
  sbmPriveRoomStart: FormControl<ILocation['sbmPriveRoomStart']>;
  sbmPriveRoomEnd: FormControl<ILocation['sbmPriveRoomEnd']>;
  sbmPmsSendAllDetails: FormControl<ILocation['sbmPmsSendAllDetails']>;
  sbmPmsSendFullDscv: FormControl<ILocation['sbmPmsSendFullDscv']>;
  sbmPmsSend64Tax: FormControl<ILocation['sbmPmsSend64Tax']>;
  sbmCardPaymentUrl: FormControl<ILocation['sbmCardPaymentUrl']>;
  sbmCheckHotelDataUrl: FormControl<ILocation['sbmCheckHotelDataUrl']>;
  sbmVoucherSvcUrl: FormControl<ILocation['sbmVoucherSvcUrl']>;
  sbmVoucherInvPm: FormControl<ILocation['sbmVoucherInvPm']>;
  sbmVoucherCorpPm: FormControl<ILocation['sbmVoucherCorpPm']>;
  sbmVoucherRewardPm: FormControl<ILocation['sbmVoucherRewardPm']>;
  sbmVoucherMcPm: FormControl<ILocation['sbmVoucherMcPm']>;
  sbmPmsIfcPort2: FormControl<ILocation['sbmPmsIfcPort2']>;
  sbmPmsIfcPort3: FormControl<ILocation['sbmPmsIfcPort3']>;
  sbmPmsIfcPort4: FormControl<ILocation['sbmPmsIfcPort4']>;
  sbmTimeout: FormControl<ILocation['sbmTimeout']>;
};

export type LocationFormGroup = FormGroup<LocationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LocationFormService {
  createLocationFormGroup(location: LocationFormGroupInput = { id: null }): LocationFormGroup {
    const locationRawValue = {
      ...this.getFormDefaults(),
      ...location,
    };
    return new FormGroup<LocationFormGroupContent>({
      id: new FormControl(
        { value: locationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      hierUnitId: new FormControl(locationRawValue.hierUnitId),
      tzIndex: new FormControl(locationRawValue.tzIndex),
      tzName: new FormControl(locationRawValue.tzName),
      localeInfoId: new FormControl(locationRawValue.localeInfoId),
      name: new FormControl(locationRawValue.name),
      reportingLocName: new FormControl(locationRawValue.reportingLocName),
      locRef: new FormControl(locationRawValue.locRef),
      reportingParentEnterpriseLevelName: new FormControl(locationRawValue.reportingParentEnterpriseLevelName),
      objectNum: new FormControl(locationRawValue.objectNum),
      sbmPmsIfcIp: new FormControl(locationRawValue.sbmPmsIfcIp),
      sbmPmsIfcPort: new FormControl(locationRawValue.sbmPmsIfcPort),
      sbmPriveRoomStart: new FormControl(locationRawValue.sbmPriveRoomStart),
      sbmPriveRoomEnd: new FormControl(locationRawValue.sbmPriveRoomEnd),
      sbmPmsSendAllDetails: new FormControl(locationRawValue.sbmPmsSendAllDetails),
      sbmPmsSendFullDscv: new FormControl(locationRawValue.sbmPmsSendFullDscv),
      sbmPmsSend64Tax: new FormControl(locationRawValue.sbmPmsSend64Tax),
      sbmCardPaymentUrl: new FormControl(locationRawValue.sbmCardPaymentUrl),
      sbmCheckHotelDataUrl: new FormControl(locationRawValue.sbmCheckHotelDataUrl),
      sbmVoucherSvcUrl: new FormControl(locationRawValue.sbmVoucherSvcUrl),
      sbmVoucherInvPm: new FormControl(locationRawValue.sbmVoucherInvPm),
      sbmVoucherCorpPm: new FormControl(locationRawValue.sbmVoucherCorpPm),
      sbmVoucherRewardPm: new FormControl(locationRawValue.sbmVoucherRewardPm),
      sbmVoucherMcPm: new FormControl(locationRawValue.sbmVoucherMcPm),
      sbmPmsIfcPort2: new FormControl(locationRawValue.sbmPmsIfcPort2),
      sbmPmsIfcPort3: new FormControl(locationRawValue.sbmPmsIfcPort3),
      sbmPmsIfcPort4: new FormControl(locationRawValue.sbmPmsIfcPort4),
      sbmTimeout: new FormControl(locationRawValue.sbmTimeout),
    });
  }

  getLocation(form: LocationFormGroup): ILocation | NewLocation {
    return form.getRawValue() as ILocation | NewLocation;
  }

  resetForm(form: LocationFormGroup, location: LocationFormGroupInput): void {
    const locationRawValue = { ...this.getFormDefaults(), ...location };
    form.reset(
      {
        ...locationRawValue,
        id: { value: locationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LocationFormDefaults {
    return {
      id: null,
    };
  }
}
