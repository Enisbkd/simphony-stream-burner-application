import dayjs from 'dayjs/esm';

import { IDetailLineBI, NewDetailLineBI } from './detail-line-bi.model';

export const sampleWithRequiredData: IDetailLineBI = {
  id: 4353,
};

export const sampleWithPartialData: IDetailLineBI = {
  id: 31867,
  detailUTC: dayjs('2025-07-28T21:11'),
  detailLcl: dayjs('2025-07-28T21:42'),
  seatNum: 29625,
  prcLvl: 11148,
  dspTtl: 8795.78,
  dspQty: 8196,
  vdFlag: true,
  rsnCodeNum: 3883,
  refInfo2: 'consequently curiously',
  parDtlId: 19264,
  chkEmpId: 17179,
  mgrEmpNum: 32002,
  mealEmpNum: 17542,
  dscNum: 9904,
};

export const sampleWithFullData: IDetailLineBI = {
  id: 2165,
  guestCheckLineItemId: 15795,
  detailUTC: dayjs('2025-07-28T15:56'),
  detailLcl: dayjs('2025-07-29T02:01'),
  seatNum: 32705,
  prcLvl: 8513,
  dspTtl: 12573.01,
  dspQty: 24851,
  errCorFlag: true,
  vdFlag: true,
  returnFlag: false,
  doNotShowFlag: true,
  aggTtl: 8309.95,
  rsnCodeNum: 21640,
  refInfo1: 'cautiously redound',
  refInfo2: 'via bungalow',
  svcRndNum: 13236,
  parDtlId: 10610,
  chkEmpId: 17472,
  transEmpNum: 17012,
  mgrEmpNum: 28318,
  mealEmpNum: 1456,
  dscNum: 13637,
  dscMiNum: 27201,
  svcChgNum: 12803,
  tmedNum: 29654,
  miNum: 25665,
};

export const sampleWithNewData: NewDetailLineBI = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
