export interface ICommissionServiceBI {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  typeValue?: string | null;
  value?: number | null;
  etablissementRef?: string | null;
}

export type NewCommissionServiceBI = Omit<ICommissionServiceBI, 'id'> & { id: null };
