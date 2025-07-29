export interface IMajorGroupCnC {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  pointDeVenteRef?: number | null;
}

export type NewMajorGroupCnC = Omit<IMajorGroupCnC, 'id'> & { id: null };
