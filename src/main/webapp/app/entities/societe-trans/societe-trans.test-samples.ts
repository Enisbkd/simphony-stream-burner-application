import { ISocieteTrans, NewSocieteTrans } from './societe-trans.model';

export const sampleWithRequiredData: ISocieteTrans = {
  id: 7293,
};

export const sampleWithPartialData: ISocieteTrans = {
  id: 19446,
};

export const sampleWithFullData: ISocieteTrans = {
  id: 20178,
  nom: 'worth',
  nomCourt: 'begonia',
};

export const sampleWithNewData: NewSocieteTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
