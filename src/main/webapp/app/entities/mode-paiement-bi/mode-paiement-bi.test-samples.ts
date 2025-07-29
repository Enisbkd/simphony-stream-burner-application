import { IModePaiementBI, NewModePaiementBI } from './mode-paiement-bi.model';

export const sampleWithRequiredData: IModePaiementBI = {
  id: 9800,
};

export const sampleWithPartialData: IModePaiementBI = {
  id: 26396,
  num: 7315,
  type: 772,
};

export const sampleWithFullData: IModePaiementBI = {
  id: 18816,
  locRef: 'bah',
  num: 4225,
  name: 'cap',
  type: 16989,
};

export const sampleWithNewData: NewModePaiementBI = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
