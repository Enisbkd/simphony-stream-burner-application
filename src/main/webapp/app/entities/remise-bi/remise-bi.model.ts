export interface IRemiseBI {
  id: number;
  num?: number | null;
  name?: string | null;
  posPercent?: number | null;
  rptGrpNum?: number | null;
  rptGrpName?: string | null;
  locRef?: string | null;
}

export type NewRemiseBI = Omit<IRemiseBI, 'id'> & { id: null };
