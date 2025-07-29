import { IMajorGroupCnC, NewMajorGroupCnC } from './major-group-cn-c.model';

export const sampleWithRequiredData: IMajorGroupCnC = {
  id: 30028,
};

export const sampleWithPartialData: IMajorGroupCnC = {
  id: 28453,
  pointDeVenteRef: 17302,
};

export const sampleWithFullData: IMajorGroupCnC = {
  id: 12711,
  nom: 'lest raw supposing',
  nomCourt: 'pupil',
  pointDeVenteRef: 6205,
};

export const sampleWithNewData: NewMajorGroupCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
