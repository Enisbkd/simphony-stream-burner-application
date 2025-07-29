export interface ILocation {
  id: number;
  hierUnitId?: number | null;
  tzIndex?: number | null;
  tzName?: string | null;
  localeInfoId?: number | null;
  name?: string | null;
  reportingLocName?: string | null;
  locRef?: string | null;
  reportingParentEnterpriseLevelName?: string | null;
  objectNum?: number | null;
  sbmPmsIfcIp?: string | null;
  sbmPmsIfcPort?: string | null;
  sbmPriveRoomStart?: string | null;
  sbmPriveRoomEnd?: string | null;
  sbmPmsSendAllDetails?: string | null;
  sbmPmsSendFullDscv?: string | null;
  sbmPmsSend64Tax?: string | null;
  sbmCardPaymentUrl?: string | null;
  sbmCheckHotelDataUrl?: string | null;
  sbmVoucherSvcUrl?: string | null;
  sbmVoucherInvPm?: string | null;
  sbmVoucherCorpPm?: string | null;
  sbmVoucherRewardPm?: string | null;
  sbmVoucherMcPm?: string | null;
  sbmPmsIfcPort2?: string | null;
  sbmPmsIfcPort3?: string | null;
  sbmPmsIfcPort4?: string | null;
  sbmTimeout?: string | null;
}

export type NewLocation = Omit<ILocation, 'id'> & { id: null };
