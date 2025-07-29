export interface IModePaiementBI {
  id: number;
  locRef?: string | null;
  num?: number | null;
  name?: string | null;
  type?: number | null;
}

export type NewModePaiementBI = Omit<IModePaiementBI, 'id'> & { id: null };
