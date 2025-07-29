import { ICommissionServiceBI, NewCommissionServiceBI } from './commission-service-bi.model';

export const sampleWithRequiredData: ICommissionServiceBI = {
  id: 7373,
  etablissementRef: 'capitalise healthily chairperson',
};

export const sampleWithPartialData: ICommissionServiceBI = {
  id: 25982,
  nom: 'upside-down',
  typeValue: 'good-natured but though',
  etablissementRef: 'impractical',
};

export const sampleWithFullData: ICommissionServiceBI = {
  id: 701,
  nom: 'actually',
  nomCourt: 'dimly as',
  typeValue: 'wearily unless',
  value: 25134.19,
  etablissementRef: 'amidst',
};

export const sampleWithNewData: NewCommissionServiceBI = {
  etablissementRef: 'tray that',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
