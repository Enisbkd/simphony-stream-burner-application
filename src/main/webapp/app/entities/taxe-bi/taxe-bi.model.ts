export interface ITaxeBI {
  id: number;
  locRef?: string | null;
  num?: number | null;
  name?: string | null;
  type?: number | null;
}

export type NewTaxeBI = Omit<ITaxeBI, 'id'> & { id: null };
