import { IHierarchieCnC, NewHierarchieCnC } from './hierarchie-cn-c.model';

export const sampleWithRequiredData: IHierarchieCnC = {
  id: 12164,
  nom: 'sleepily',
};

export const sampleWithPartialData: IHierarchieCnC = {
  id: 11177,
  nom: 'gray alongside',
  unitId: 'after vamoose',
};

export const sampleWithFullData: IHierarchieCnC = {
  id: 28751,
  nom: 'of ripe',
  parentHierId: 'thin advanced',
  unitId: 'when like',
};

export const sampleWithNewData: NewHierarchieCnC = {
  nom: 'status though extra-large',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
