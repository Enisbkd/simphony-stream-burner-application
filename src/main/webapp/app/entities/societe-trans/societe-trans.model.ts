export interface ISocieteTrans {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
}

export type NewSocieteTrans = Omit<ISocieteTrans, 'id'> & { id: null };
