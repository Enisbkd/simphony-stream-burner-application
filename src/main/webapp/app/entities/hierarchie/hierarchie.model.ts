export interface IHierarchie {
  id: number;
  nom?: string | null;
  parentHierId?: string | null;
  unitId?: string | null;
}

export type NewHierarchie = Omit<IHierarchie, 'id'> & { id: null };
