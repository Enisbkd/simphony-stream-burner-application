import { IMajorGroup, NewMajorGroup } from './major-group.model';

export const sampleWithRequiredData: IMajorGroup = {
  id: 4235,
};

export const sampleWithPartialData: IMajorGroup = {
  id: 10797,
  nom: 'failing wrongly perfectly',
};

export const sampleWithFullData: IMajorGroup = {
  id: 21304,
  nom: 'nor',
  nomCourt: 'wisely ugh',
  pointDeVenteRef: 29142,
};

export const sampleWithNewData: NewMajorGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
