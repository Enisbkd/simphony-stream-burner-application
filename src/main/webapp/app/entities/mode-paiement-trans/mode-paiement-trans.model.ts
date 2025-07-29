export interface IModePaiementTrans {
  id: number;
  tenderId?: number | null;
  name?: string | null;
  type?: string | null;
  extensions?: string | null;
  orgShortName?: string | null;
  locRef?: string | null;
  rvcRef?: number | null;
}

export type NewModePaiementTrans = Omit<IModePaiementTrans, 'id'> & { id: null };
