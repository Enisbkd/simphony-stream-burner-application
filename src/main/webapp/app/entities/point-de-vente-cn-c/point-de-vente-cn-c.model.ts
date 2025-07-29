export interface IPointDeVenteCnC {
  id: number;
  locHierUnitId?: number | null;
  locObjNum?: number | null;
  rvcId?: number | null;
  kdsControllerId?: number | null;
  hierUnitId?: number | null;
  objectNum?: number | null;
  name?: string | null;
  dataExtensions?: string | null;
}

export type NewPointDeVenteCnC = Omit<IPointDeVenteCnC, 'id'> & { id: null };
