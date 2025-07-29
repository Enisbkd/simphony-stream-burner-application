import { IFamilyGroupCnC, NewFamilyGroupCnC } from './family-group-cn-c.model';

export const sampleWithRequiredData: IFamilyGroupCnC = {
  id: 14961,
};

export const sampleWithPartialData: IFamilyGroupCnC = {
  id: 23977,
  nom: 'ah',
  nomCourt: 'that exalt madly',
  majorGroupRef: 23552,
};

export const sampleWithFullData: IFamilyGroupCnC = {
  id: 23397,
  nom: 'more duh',
  nomCourt: 'accidentally throughout in-joke',
  majorGroupRef: 7630,
};

export const sampleWithNewData: NewFamilyGroupCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
