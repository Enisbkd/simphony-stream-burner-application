export interface IPointDeVenteTrans {
  id: number;
  rvcRef?: number | null;
  name?: string | null;
  locRef?: string | null;
  orgShortName?: string | null;
  address?: string | null;
}

export type NewPointDeVenteTrans = Omit<IPointDeVenteTrans, 'id'> & { id: null };
