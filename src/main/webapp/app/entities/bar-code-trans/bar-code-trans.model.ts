export interface IBarCodeTrans {
  id: number;
  locRef?: string | null;
  rvcRef?: number | null;
  barcodeId?: number | null;
  barcode?: string | null;
  menuItemId?: number | null;
  defenitionSequence?: number | null;
  price?: number | null;
  priceSequence?: number | null;
  preparationCost?: number | null;
}

export type NewBarCodeTrans = Omit<IBarCodeTrans, 'id'> & { id: null };
