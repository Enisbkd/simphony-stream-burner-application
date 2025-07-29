import { IOrganizationLocationTrans, NewOrganizationLocationTrans } from './organization-location-trans.model';

export const sampleWithRequiredData: IOrganizationLocationTrans = {
  id: 19985,
};

export const sampleWithPartialData: IOrganizationLocationTrans = {
  id: 19865,
  locRef: 'vicinity back down',
  currency: 'rust courageously along',
  phoneNumber: 'following probe sentimental',
  languages: 'what afore well',
  timezoneWindowsName: 'until disbar',
  addressLine1: 'in per sin',
  addressRegion: 'preclude daily vice',
  addressPostalCode: 'which eek gadzooks',
  addressCountry: 'noisily',
  posPlatformName: 'lest',
};

export const sampleWithFullData: IOrganizationLocationTrans = {
  id: 18655,
  orgShortName: 'gosh',
  locRef: 'dutiful',
  name: 'save towards modulo',
  currency: 'of',
  phoneNumber: 'gradient',
  languages: 'bootleg',
  timezoneIanaName: 'chainstay',
  timezoneWindowsName: 'while',
  timezoneTzIndex: 24000,
  addressLine1: 'from',
  addressLine2: 'curiously',
  addressFloor: 'popularity',
  addressLocality: 'colorfully',
  addressRegion: 'boohoo ha smarten',
  addressPostalCode: 'fussy uh-huh hm',
  addressCountry: 'progress',
  addressNotes: 'nice',
  geoLatitude: 19438.05,
  geoLongitude: 2001.64,
  posPlatformName: 'rarely',
  posPlatformVersion: 'commonly',
};

export const sampleWithNewData: NewOrganizationLocationTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
