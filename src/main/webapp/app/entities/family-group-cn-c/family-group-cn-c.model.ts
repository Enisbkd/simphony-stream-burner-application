export interface IFamilyGroupCnC {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  majorGroupRef?: number | null;
}

export type NewFamilyGroupCnC = Omit<IFamilyGroupCnC, 'id'> & { id: null };
