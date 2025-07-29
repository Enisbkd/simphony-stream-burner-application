export interface IOrganizationLocationTrans {
  id: number;
  orgShortName?: string | null;
  locRef?: string | null;
  name?: string | null;
  currency?: string | null;
  phoneNumber?: string | null;
  languages?: string | null;
  timezoneIanaName?: string | null;
  timezoneWindowsName?: string | null;
  timezoneTzIndex?: number | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  addressFloor?: string | null;
  addressLocality?: string | null;
  addressRegion?: string | null;
  addressPostalCode?: string | null;
  addressCountry?: string | null;
  addressNotes?: string | null;
  geoLatitude?: number | null;
  geoLongitude?: number | null;
  posPlatformName?: string | null;
  posPlatformVersion?: string | null;
}

export type NewOrganizationLocationTrans = Omit<IOrganizationLocationTrans, 'id'> & { id: null };
