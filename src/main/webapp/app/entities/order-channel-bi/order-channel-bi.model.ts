export interface IOrderChannelBI {
  id: number;
  num?: number | null;
  locRef?: string | null;
  name?: string | null;
  mstrNum?: number | null;
  mstrName?: string | null;
}

export type NewOrderChannelBI = Omit<IOrderChannelBI, 'id'> & { id: null };
