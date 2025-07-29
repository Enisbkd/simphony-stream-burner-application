export interface IOrderChannel {
  id: number;
  num?: number | null;
  locRef?: string | null;
  name?: string | null;
  mstrNum?: number | null;
  mstrName?: string | null;
}

export type NewOrderChannel = Omit<IOrderChannel, 'id'> & { id: null };
