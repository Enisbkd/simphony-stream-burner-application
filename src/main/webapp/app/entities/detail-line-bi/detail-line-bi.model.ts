import dayjs from 'dayjs/esm';
import { IGuestCheckBI } from 'app/entities/guest-check-bi/guest-check-bi.model';

export interface IDetailLineBI {
  id: number;
  guestCheckLineItemId?: number | null;
  detailUTC?: dayjs.Dayjs | null;
  detailLcl?: dayjs.Dayjs | null;
  seatNum?: number | null;
  prcLvl?: number | null;
  dspTtl?: number | null;
  dspQty?: number | null;
  errCorFlag?: boolean | null;
  vdFlag?: boolean | null;
  returnFlag?: boolean | null;
  doNotShowFlag?: boolean | null;
  aggTtl?: number | null;
  rsnCodeNum?: number | null;
  refInfo1?: string | null;
  refInfo2?: string | null;
  svcRndNum?: number | null;
  parDtlId?: number | null;
  chkEmpId?: number | null;
  transEmpNum?: number | null;
  mgrEmpNum?: number | null;
  mealEmpNum?: number | null;
  dscNum?: number | null;
  dscMiNum?: number | null;
  svcChgNum?: number | null;
  tmedNum?: number | null;
  miNum?: number | null;
  guestCheckBI?: IGuestCheckBI | null;
}

export type NewDetailLineBI = Omit<IDetailLineBI, 'id'> & { id: null };
