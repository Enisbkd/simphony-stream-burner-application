import { IFamilyGroup, NewFamilyGroup } from './family-group.model';

export const sampleWithRequiredData: IFamilyGroup = {
  id: 10934,
};

export const sampleWithPartialData: IFamilyGroup = {
  id: 22514,
  nom: 'waltz across barracks',
  nomCourt: 'ouch compassionate electrify',
  majorGroupRef: 5228,
};

export const sampleWithFullData: IFamilyGroup = {
  id: 18934,
  nom: 'duh amend farmer',
  nomCourt: 'until why mom',
  majorGroupRef: 13352,
};

export const sampleWithNewData: NewFamilyGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
