export interface IOrderTypeBI {
  id: number;
  num?: number | null;
  locRef?: string | null;
  name?: string | null;
  mstrNum?: number | null;
  mstrName?: string | null;
  catGrpHierName1?: string | null;
  catGrpName1?: string | null;
  catGrpHierName2?: string | null;
  catGrpName2?: string | null;
  catGrpHierName3?: string | null;
  catGrpName3?: string | null;
  catGrpHierName4?: string | null;
  catGrpName4?: string | null;
}

export type NewOrderTypeBI = Omit<IOrderTypeBI, 'id'> & { id: null };
