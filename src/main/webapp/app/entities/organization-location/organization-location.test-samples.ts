import { IOrganizationLocation, NewOrganizationLocation } from './organization-location.model';

export const sampleWithRequiredData: IOrganizationLocation = {
  id: 7875,
};

export const sampleWithPartialData: IOrganizationLocation = {
  id: 3188,
  orgShortName: 'voluntarily',
  locRef: 'when yippee',
  name: 'besides desk athwart',
  currency: 'vastly',
  phoneNumber: 'that',
  addressLine2: 'viciously',
  addressLocality: 'instead pace',
  addressPostalCode: 'that proliferate',
  geoLongitude: 3526.13,
  posPlatformName: 'although',
  posPlatformVersion: 'nor',
};

export const sampleWithFullData: IOrganizationLocation = {
  id: 19285,
  orgShortName: 'lieu deficient',
  locRef: 'because',
  name: 'finally though trash',
  currency: 'meanwhile airmail modulo',
  phoneNumber: 'inside glittering inside',
  languages: 'woot and yet',
  timezoneIanaName: 'mobilise wash dirty',
  timezoneWindowsName: 'snack',
  timezoneTzIndex: 713,
  addressLine1: 'clavicle',
  addressLine2: 'urgently',
  addressFloor: 'scamper atop metabolise',
  addressLocality: 'amazing',
  addressRegion: 'potable yearningly',
  addressPostalCode: 'usefully',
  addressCountry: 'suspiciously',
  addressNotes: 'though',
  geoLatitude: 25142.99,
  geoLongitude: 166.88,
  posPlatformName: 'beside outside',
  posPlatformVersion: 'wafer recount indeed',
};

export const sampleWithNewData: NewOrganizationLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
