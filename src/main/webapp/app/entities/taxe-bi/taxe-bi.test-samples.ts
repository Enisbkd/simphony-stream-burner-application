import { ITaxeBI, NewTaxeBI } from './taxe-bi.model';

export const sampleWithRequiredData: ITaxeBI = {
  id: 6980,
};

export const sampleWithPartialData: ITaxeBI = {
  id: 6330,
  num: 30552,
  type: 130,
};

export const sampleWithFullData: ITaxeBI = {
  id: 18021,
  locRef: 'daily',
  num: 6998,
  name: 'venture cap unit',
  type: 23106,
};

export const sampleWithNewData: NewTaxeBI = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
