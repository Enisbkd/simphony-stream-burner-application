import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrganizationLocation, NewOrganizationLocation } from '../organization-location.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganizationLocation for edit and NewOrganizationLocationFormGroupInput for create.
 */
type OrganizationLocationFormGroupInput = IOrganizationLocation | PartialWithRequiredKeyOf<NewOrganizationLocation>;

type OrganizationLocationFormDefaults = Pick<NewOrganizationLocation, 'id'>;

type OrganizationLocationFormGroupContent = {
  id: FormControl<IOrganizationLocation['id'] | NewOrganizationLocation['id']>;
  orgShortName: FormControl<IOrganizationLocation['orgShortName']>;
  locRef: FormControl<IOrganizationLocation['locRef']>;
  name: FormControl<IOrganizationLocation['name']>;
  currency: FormControl<IOrganizationLocation['currency']>;
  phoneNumber: FormControl<IOrganizationLocation['phoneNumber']>;
  languages: FormControl<IOrganizationLocation['languages']>;
  timezoneIanaName: FormControl<IOrganizationLocation['timezoneIanaName']>;
  timezoneWindowsName: FormControl<IOrganizationLocation['timezoneWindowsName']>;
  timezoneTzIndex: FormControl<IOrganizationLocation['timezoneTzIndex']>;
  addressLine1: FormControl<IOrganizationLocation['addressLine1']>;
  addressLine2: FormControl<IOrganizationLocation['addressLine2']>;
  addressFloor: FormControl<IOrganizationLocation['addressFloor']>;
  addressLocality: FormControl<IOrganizationLocation['addressLocality']>;
  addressRegion: FormControl<IOrganizationLocation['addressRegion']>;
  addressPostalCode: FormControl<IOrganizationLocation['addressPostalCode']>;
  addressCountry: FormControl<IOrganizationLocation['addressCountry']>;
  addressNotes: FormControl<IOrganizationLocation['addressNotes']>;
  geoLatitude: FormControl<IOrganizationLocation['geoLatitude']>;
  geoLongitude: FormControl<IOrganizationLocation['geoLongitude']>;
  posPlatformName: FormControl<IOrganizationLocation['posPlatformName']>;
  posPlatformVersion: FormControl<IOrganizationLocation['posPlatformVersion']>;
};

export type OrganizationLocationFormGroup = FormGroup<OrganizationLocationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizationLocationFormService {
  createOrganizationLocationFormGroup(
    organizationLocation: OrganizationLocationFormGroupInput = { id: null },
  ): OrganizationLocationFormGroup {
    const organizationLocationRawValue = {
      ...this.getFormDefaults(),
      ...organizationLocation,
    };
    return new FormGroup<OrganizationLocationFormGroupContent>({
      id: new FormControl(
        { value: organizationLocationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      orgShortName: new FormControl(organizationLocationRawValue.orgShortName),
      locRef: new FormControl(organizationLocationRawValue.locRef),
      name: new FormControl(organizationLocationRawValue.name),
      currency: new FormControl(organizationLocationRawValue.currency),
      phoneNumber: new FormControl(organizationLocationRawValue.phoneNumber),
      languages: new FormControl(organizationLocationRawValue.languages),
      timezoneIanaName: new FormControl(organizationLocationRawValue.timezoneIanaName),
      timezoneWindowsName: new FormControl(organizationLocationRawValue.timezoneWindowsName),
      timezoneTzIndex: new FormControl(organizationLocationRawValue.timezoneTzIndex),
      addressLine1: new FormControl(organizationLocationRawValue.addressLine1),
      addressLine2: new FormControl(organizationLocationRawValue.addressLine2),
      addressFloor: new FormControl(organizationLocationRawValue.addressFloor),
      addressLocality: new FormControl(organizationLocationRawValue.addressLocality),
      addressRegion: new FormControl(organizationLocationRawValue.addressRegion),
      addressPostalCode: new FormControl(organizationLocationRawValue.addressPostalCode),
      addressCountry: new FormControl(organizationLocationRawValue.addressCountry),
      addressNotes: new FormControl(organizationLocationRawValue.addressNotes),
      geoLatitude: new FormControl(organizationLocationRawValue.geoLatitude),
      geoLongitude: new FormControl(organizationLocationRawValue.geoLongitude),
      posPlatformName: new FormControl(organizationLocationRawValue.posPlatformName),
      posPlatformVersion: new FormControl(organizationLocationRawValue.posPlatformVersion),
    });
  }

  getOrganizationLocation(form: OrganizationLocationFormGroup): IOrganizationLocation | NewOrganizationLocation {
    return form.getRawValue() as IOrganizationLocation | NewOrganizationLocation;
  }

  resetForm(form: OrganizationLocationFormGroup, organizationLocation: OrganizationLocationFormGroupInput): void {
    const organizationLocationRawValue = { ...this.getFormDefaults(), ...organizationLocation };
    form.reset(
      {
        ...organizationLocationRawValue,
        id: { value: organizationLocationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrganizationLocationFormDefaults {
    return {
      id: null,
    };
  }
}
