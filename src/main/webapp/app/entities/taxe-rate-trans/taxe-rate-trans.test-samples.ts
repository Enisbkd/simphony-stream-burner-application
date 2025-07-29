import { ITaxeRateTrans, NewTaxeRateTrans } from './taxe-rate-trans.model';

export const sampleWithRequiredData: ITaxeRateTrans = {
  id: 22735,
};

export const sampleWithPartialData: ITaxeRateTrans = {
  id: 1985,
  taxRateId: 6582,
  percentage: 27741.5,
  nameEN: 'shadowbox',
};

export const sampleWithFullData: ITaxeRateTrans = {
  id: 10721,
  orgShortName: 'mid unsung',
  locRef: 'availability boo',
  rvcRef: 32232,
  taxRateId: 23136,
  percentage: 28071.95,
  taxType: 'almost now',
  nameFR: 'festival',
  nameEN: 'freight aha',
};

export const sampleWithNewData: NewTaxeRateTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
