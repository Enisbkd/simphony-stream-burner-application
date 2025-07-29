export interface ISociete {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
}

export type NewSociete = Omit<ISociete, 'id'> & { id: null };
