import dayjs from 'dayjs/esm';

import { ICheckTrans, NewCheckTrans } from './check-trans.model';

export const sampleWithRequiredData: ICheckTrans = {
  id: 16717,
};

export const sampleWithPartialData: ICheckTrans = {
  id: 13715,
  rvcRef: 24465,
  checkNumber: 8505,
  checkName: 'definitive',
  orderTypeRef: 13539,
  orderChannelRef: 13321,
  openTime: dayjs('2025-07-28T20:22'),
  guestCount: 24891,
  status: 'before repurpose very',
  preparationStatus: 'meh',
  subtotalDiscountTotal: 2288.79,
  autoServiceChargeTotal: 22932.11,
  serviceChargeTotal: 17113.97,
  taxTotal: 23932.37,
  paymentTotal: 29774.81,
  totalDue: 27632.32,
  taxRateId: 23073,
};

export const sampleWithFullData: ICheckTrans = {
  id: 31842,
  rvcRef: 11870,
  checkRef: 'nauseate whose hasty',
  checkNumber: 21527,
  checkName: 'tiny round',
  checkEmployeeRef: 32358,
  orderTypeRef: 15237,
  orderChannelRef: 24358,
  tableName: 'helpfully',
  tableGroupNumber: 32564,
  openTime: dayjs('2025-07-28T18:38'),
  guestCount: 13367,
  language: 'silently barring unless',
  isTrainingCheck: false,
  status: 'skeleton while structure',
  preparationStatus: 'huzzah',
  subtotal: 18360.53,
  subtotalDiscountTotal: 15632.28,
  autoServiceChargeTotal: 22925.1,
  serviceChargeTotal: 14721.29,
  taxTotal: 28523.45,
  paymentTotal: 25869.56,
  totalDue: 17174.7,
  taxRateId: 10434,
};

export const sampleWithNewData: NewCheckTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
