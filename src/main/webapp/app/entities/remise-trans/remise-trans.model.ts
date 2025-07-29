export interface IRemiseTrans {
  id: number;
  orgShortName?: string | null;
  locRef?: string | null;
  rvcRef?: number | null;
  discountId?: number | null;
  frName?: string | null;
  engName?: string | null;
  discountType?: string | null;
  discountValue?: number | null;
}

export type NewRemiseTrans = Omit<IRemiseTrans, 'id'> & { id: null };
