export interface ITaxeClassTrans {
  id: number;
  orgShortName?: string | null;
  locRef?: string | null;
  rvcRef?: number | null;
  taxClassId?: number | null;
  activeTaxRateRefs?: string | null;
}

export type NewTaxeClassTrans = Omit<ITaxeClassTrans, 'id'> & { id: null };
