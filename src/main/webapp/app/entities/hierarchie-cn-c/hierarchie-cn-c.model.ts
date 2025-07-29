export interface IHierarchieCnC {
  id: number;
  nom?: string | null;
  parentHierId?: string | null;
  unitId?: string | null;
}

export type NewHierarchieCnC = Omit<IHierarchieCnC, 'id'> & { id: null };
