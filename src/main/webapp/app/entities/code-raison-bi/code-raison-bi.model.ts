export interface ICodeRaisonBI {
  id: number;
  nomCourt?: number | null;
  nomMstr?: string | null;
  numMstr?: number | null;
  name?: string | null;
  etablissementRef?: string | null;
}

export type NewCodeRaisonBI = Omit<ICodeRaisonBI, 'id'> & { id: null };
