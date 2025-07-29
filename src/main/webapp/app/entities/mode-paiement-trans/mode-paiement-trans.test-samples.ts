import { IModePaiementTrans, NewModePaiementTrans } from './mode-paiement-trans.model';

export const sampleWithRequiredData: IModePaiementTrans = {
  id: 26663,
};

export const sampleWithPartialData: IModePaiementTrans = {
  id: 10760,
  tenderId: 29502,
  name: 'spear',
  extensions: 'shoulder warped',
  orgShortName: 'amazing amongst',
  locRef: 'antique',
  rvcRef: 29266,
};

export const sampleWithFullData: IModePaiementTrans = {
  id: 21534,
  tenderId: 14179,
  name: 'part',
  type: 'courageously how although',
  extensions: 'concerning',
  orgShortName: 'rectangular remark',
  locRef: 'ha',
  rvcRef: 26659,
};

export const sampleWithNewData: NewModePaiementTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
