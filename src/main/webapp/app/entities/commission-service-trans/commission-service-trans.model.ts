export interface ICommissionServiceTrans {
  id: number;
  orgShortName?: string | null;
  locRef?: string | null;
  rvcRef?: number | null;
  serviceChargeId?: number | null;
  name?: string | null;
  type?: string | null;
  value?: number | null;
}

export type NewCommissionServiceTrans = Omit<ICommissionServiceTrans, 'id'> & { id: null };
