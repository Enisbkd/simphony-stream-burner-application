import { ITaxeClassTrans, NewTaxeClassTrans } from './taxe-class-trans.model';

export const sampleWithRequiredData: ITaxeClassTrans = {
  id: 18310,
};

export const sampleWithPartialData: ITaxeClassTrans = {
  id: 5365,
  rvcRef: 15004,
  taxClassId: 16090,
  activeTaxRateRefs: 'devise unexpectedly sunny',
};

export const sampleWithFullData: ITaxeClassTrans = {
  id: 12320,
  orgShortName: 'yellowish',
  locRef: 'outdo',
  rvcRef: 22235,
  taxClassId: 27450,
  activeTaxRateRefs: 'except',
};

export const sampleWithNewData: NewTaxeClassTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
