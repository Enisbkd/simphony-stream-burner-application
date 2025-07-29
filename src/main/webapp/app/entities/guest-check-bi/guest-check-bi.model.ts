import dayjs from 'dayjs/esm';

export interface IGuestCheckBI {
  id: number;
  guestCheckId?: number | null;
  chkNum?: number | null;
  opnLcl?: dayjs.Dayjs | null;
  clsdLcl?: dayjs.Dayjs | null;
  cancelFlag?: boolean | null;
  gstCnt?: number | null;
  tblNum?: number | null;
  taxCollTtl?: number | null;
  subTtl?: number | null;
  chkTtl?: number | null;
  svcChgTtl?: number | null;
  tipTotal?: number | null;
  dscTtl?: number | null;
  errorCorrectTtl?: number | null;
  returnTtl?: number | null;
  xferToChkNum?: number | null;
  xferStatus?: string | null;
  otNum?: number | null;
  locRef?: string | null;
}

export type NewGuestCheckBI = Omit<IGuestCheckBI, 'id'> & { id: null };
