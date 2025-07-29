import { IHierarchie, NewHierarchie } from './hierarchie.model';

export const sampleWithRequiredData: IHierarchie = {
  id: 5606,
  nom: 'sometimes better',
};

export const sampleWithPartialData: IHierarchie = {
  id: 26078,
  nom: 'or',
  parentHierId: 'dependency',
  unitId: 'along',
};

export const sampleWithFullData: IHierarchie = {
  id: 12475,
  nom: 'like',
  parentHierId: 'hm lighthearted abaft',
  unitId: 'modulo supposing',
};

export const sampleWithNewData: NewHierarchie = {
  nom: 'minus',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
