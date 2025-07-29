import { ISociete, NewSociete } from './societe.model';

export const sampleWithRequiredData: ISociete = {
  id: 32433,
};

export const sampleWithPartialData: ISociete = {
  id: 27239,
};

export const sampleWithFullData: ISociete = {
  id: 14789,
  nom: 'zowie hover gripping',
  nomCourt: 'until',
};

export const sampleWithNewData: NewSociete = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
