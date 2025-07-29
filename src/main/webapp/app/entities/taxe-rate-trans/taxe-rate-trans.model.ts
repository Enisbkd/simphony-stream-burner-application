export interface ITaxeRateTrans {
  id: number;
  orgShortName?: string | null;
  locRef?: string | null;
  rvcRef?: number | null;
  taxRateId?: number | null;
  percentage?: number | null;
  taxType?: string | null;
  nameFr?: string | null;
  nameEn?: string | null;
}

export type NewTaxeRateTrans = Omit<ITaxeRateTrans, 'id'> & { id: null };
