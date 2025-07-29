import dayjs from 'dayjs/esm';

import { IGuestCheckBI, NewGuestCheckBI } from './guest-check-bi.model';

export const sampleWithRequiredData: IGuestCheckBI = {
  id: 1346,
};

export const sampleWithPartialData: IGuestCheckBI = {
  id: 8079,
  chkNum: 30098,
  opnLcl: dayjs('2025-07-28T17:28'),
  cancelFlag: true,
  taxCollTtl: 17102.57,
  subTtl: 16183.07,
  returnTtl: 14441.13,
  xferToChkNum: 8908,
  locRef: 'outside machine nab',
};

export const sampleWithFullData: IGuestCheckBI = {
  id: 21077,
  guestCheckId: 24408,
  chkNum: 26580,
  opnLcl: dayjs('2025-07-29T11:16'),
  clsdLcl: dayjs('2025-07-28T14:39'),
  cancelFlag: true,
  gstCnt: 30360,
  tblNum: 3879,
  taxCollTtl: 7283.47,
  subTtl: 8757.6,
  chkTtl: 16443.88,
  svcChgTtl: 24858.45,
  tipTotal: 29132.47,
  dscTtl: 9266.05,
  errorCorrectTtl: 8286.18,
  returnTtl: 12915.03,
  xferToChkNum: 9735,
  xferStatus: 'frivolous zowie',
  otNum: 3906,
  locRef: 'rawhide',
};

export const sampleWithNewData: NewGuestCheckBI = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
