import { IBarCodeTrans, NewBarCodeTrans } from './bar-code-trans.model';

export const sampleWithRequiredData: IBarCodeTrans = {
  id: 23775,
};

export const sampleWithPartialData: IBarCodeTrans = {
  id: 28838,
  locRef: 'er splendid youthfully',
  rvcRef: 29917,
  menuItemId: 7317,
  priceSequence: 10753,
};

export const sampleWithFullData: IBarCodeTrans = {
  id: 17439,
  locRef: 'draw within',
  rvcRef: 31968,
  barcodeId: 31687,
  barcode: 'astride',
  menuItemId: 18997,
  defenitionSequence: 15679,
  price: 975.2,
  priceSequence: 11191,
  preparationCost: 22863.19,
};

export const sampleWithNewData: NewBarCodeTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
