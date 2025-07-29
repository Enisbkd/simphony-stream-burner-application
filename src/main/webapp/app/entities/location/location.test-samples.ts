import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 12482,
};

export const sampleWithPartialData: ILocation = {
  id: 2741,
  tzIndex: 27715,
  tzName: 'lounge pivot across',
  reportingLocName: 'likewise where uh-huh',
  locRef: 'gadzooks persecute zesty',
  reportingParentEnterpriseLevelName: 'snappy',
  objectNum: 14300,
  sbmPmsSendFullDscv: 'gah nice',
  sbmPmsSend64Tax: 'phooey',
  sbmCardPaymentUrl: 'afore',
  sbmCheckHotelDataUrl: 'smog majestically',
  sbmVoucherInvPm: 'blah unless',
  sbmVoucherMcPm: 'small carefully',
  sbmPmsIfcPort3: 'kowtow treble',
  sbmPmsIfcPort4: 'relative phooey hm',
  sbmTimeout: 'yowza',
};

export const sampleWithFullData: ILocation = {
  id: 22007,
  hierUnitId: 12210,
  tzIndex: 14307,
  tzName: 'absent alive',
  localeInfoId: 13604,
  name: 'typeface in where',
  reportingLocName: 'sweet cemetery quickly',
  locRef: 'bah blank hmph',
  reportingParentEnterpriseLevelName: 'sophisticated even',
  objectNum: 540,
  sbmPmsIfcIp: 'slushy',
  sbmPmsIfcPort: 'icy miserably',
  sbmPriveRoomStart: 'aside',
  sbmPriveRoomEnd: 'searchingly cultivated er',
  sbmPmsSendAllDetails: 'nor certainly analogy',
  sbmPmsSendFullDscv: 'forenenst uh-huh whenever',
  sbmPmsSend64Tax: 'basic hmph lamp',
  sbmCardPaymentUrl: 'dusk',
  sbmCheckHotelDataUrl: 'shakily',
  sbmVoucherSvcUrl: 'certainly wordy',
  sbmVoucherInvPm: 'without',
  sbmVoucherCorpPm: 'along however sick',
  sbmVoucherRewardPm: 'zowie along quarterly',
  sbmVoucherMcPm: 'drat superficial',
  sbmPmsIfcPort2: 'but',
  sbmPmsIfcPort3: 'tennis about',
  sbmPmsIfcPort4: 'in pace',
  sbmTimeout: 'unpleasant',
};

export const sampleWithNewData: NewLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
