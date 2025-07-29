import dayjs from 'dayjs/esm';

export interface ICheckTrans {
  id: number;
  rvcRef?: number | null;
  checkRef?: string | null;
  checkNumber?: number | null;
  checkName?: string | null;
  checkEmployeeRef?: number | null;
  orderTypeRef?: number | null;
  orderChannelRef?: number | null;
  tableName?: string | null;
  tableGroupNumber?: number | null;
  openTime?: dayjs.Dayjs | null;
  guestCount?: number | null;
  language?: string | null;
  isTrainingCheck?: boolean | null;
  status?: string | null;
  preparationStatus?: string | null;
  subtotal?: number | null;
  subtotalDiscountTotal?: number | null;
  autoServiceChargeTotal?: number | null;
  serviceChargeTotal?: number | null;
  taxTotal?: number | null;
  paymentTotal?: number | null;
  totalDue?: number | null;
  taxRateId?: number | null;
}

export type NewCheckTrans = Omit<ICheckTrans, 'id'> & { id: null };
