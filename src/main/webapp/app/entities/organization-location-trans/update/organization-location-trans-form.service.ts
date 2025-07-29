import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrganizationLocationTrans, NewOrganizationLocationTrans } from '../organization-location-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganizationLocationTrans for edit and NewOrganizationLocationTransFormGroupInput for create.
 */
type OrganizationLocationTransFormGroupInput = IOrganizationLocationTrans | PartialWithRequiredKeyOf<NewOrganizationLocationTrans>;

type OrganizationLocationTransFormDefaults = Pick<NewOrganizationLocationTrans, 'id'>;

type OrganizationLocationTransFormGroupContent = {
  id: FormControl<IOrganizationLocationTrans['id'] | NewOrganizationLocationTrans['id']>;
  orgShortName: FormControl<IOrganizationLocationTrans['orgShortName']>;
  locRef: FormControl<IOrganizationLocationTrans['locRef']>;
  name: FormControl<IOrganizationLocationTrans['name']>;
  currency: FormControl<IOrganizationLocationTrans['currency']>;
  phoneNumber: FormControl<IOrganizationLocationTrans['phoneNumber']>;
  languages: FormControl<IOrganizationLocationTrans['languages']>;
  timezoneIanaName: FormControl<IOrganizationLocationTrans['timezoneIanaName']>;
  timezoneWindowsName: FormControl<IOrganizationLocationTrans['timezoneWindowsName']>;
  timezoneTzIndex: FormControl<IOrganizationLocationTrans['timezoneTzIndex']>;
  addressLine1: FormControl<IOrganizationLocationTrans['addressLine1']>;
  addressLine2: FormControl<IOrganizationLocationTrans['addressLine2']>;
  addressFloor: FormControl<IOrganizationLocationTrans['addressFloor']>;
  addressLocality: FormControl<IOrganizationLocationTrans['addressLocality']>;
  addressRegion: FormControl<IOrganizationLocationTrans['addressRegion']>;
  addressPostalCode: FormControl<IOrganizationLocationTrans['addressPostalCode']>;
  addressCountry: FormControl<IOrganizationLocationTrans['addressCountry']>;
  addressNotes: FormControl<IOrganizationLocationTrans['addressNotes']>;
  geoLatitude: FormControl<IOrganizationLocationTrans['geoLatitude']>;
  geoLongitude: FormControl<IOrganizationLocationTrans['geoLongitude']>;
  posPlatformName: FormControl<IOrganizationLocationTrans['posPlatformName']>;
  posPlatformVersion: FormControl<IOrganizationLocationTrans['posPlatformVersion']>;
};

export type OrganizationLocationTransFormGroup = FormGroup<OrganizationLocationTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationLocationTransFormService {
  createOrganizationLocationTransFormGroup(
    organizationLocationTrans: OrganizationLocationTransFormGroupInput = { id: null },
  ): OrganizationLocationTransFormGroup {
    const organizationLocationTransRawValue = {
      ...this.getFormDefaults(),
      ...organizationLocationTrans,
    };
    return new FormGroup<OrganizationLocationTransFormGroupContent>({
      id: new FormControl(
        { value: organizationLocationTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      orgShortName: new FormControl(organizationLocationTransRawValue.orgShortName),
      locRef: new FormControl(organizationLocationTransRawValue.locRef),
      name: new FormControl(organizationLocationTransRawValue.name),
      currency: new FormControl(organizationLocationTransRawValue.currency),
      phoneNumber: new FormControl(organizationLocationTransRawValue.phoneNumber),
      languages: new FormControl(organizationLocationTransRawValue.languages),
      timezoneIanaName: new FormControl(organizationLocationTransRawValue.timezoneIanaName),
      timezoneWindowsName: new FormControl(organizationLocationTransRawValue.timezoneWindowsName),
      timezoneTzIndex: new FormControl(organizationLocationTransRawValue.timezoneTzIndex),
      addressLine1: new FormControl(organizationLocationTransRawValue.addressLine1),
      addressLine2: new FormControl(organizationLocationTransRawValue.addressLine2),
      addressFloor: new FormControl(organizationLocationTransRawValue.addressFloor),
      addressLocality: new FormControl(organizationLocationTransRawValue.addressLocality),
      addressRegion: new FormControl(organizationLocationTransRawValue.addressRegion),
      addressPostalCode: new FormControl(organizationLocationTransRawValue.addressPostalCode),
      addressCountry: new FormControl(organizationLocationTransRawValue.addressCountry),
      addressNotes: new FormControl(organizationLocationTransRawValue.addressNotes),
      geoLatitude: new FormControl(organizationLocationTransRawValue.geoLatitude),
      geoLongitude: new FormControl(organizationLocationTransRawValue.geoLongitude),
      posPlatformName: new FormControl(organizationLocationTransRawValue.posPlatformName),
      posPlatformVersion: new FormControl(organizationLocationTransRawValue.posPlatformVersion),
    });
  }

  getOrganizationLocationTrans(form: OrganizationLocationTransFormGroup): IOrganizationLocationTrans | NewOrganizationLocationTrans {
    return form.getRawValue() as IOrganizationLocationTrans | NewOrganizationLocationTrans;
  }

  resetForm(form: OrganizationLocationTransFormGroup, organizationLocationTrans: OrganizationLocationTransFormGroupInput): void {
    const organizationLocationTransRawValue = { ...this.getFormDefaults(), ...organizationLocationTrans };
    form.reset(
      {
        ...organizationLocationTransRawValue,
        id: { value: organizationLocationTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrganizationLocationTransFormDefaults {
    return {
      id: null,
    };
  }
}
