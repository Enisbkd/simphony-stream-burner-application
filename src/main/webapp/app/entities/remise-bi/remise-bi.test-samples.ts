import { IRemiseBI, NewRemiseBI } from './remise-bi.model';

export const sampleWithRequiredData: IRemiseBI = {
  id: 6127,
};

export const sampleWithPartialData: IRemiseBI = {
  id: 20025,
};

export const sampleWithFullData: IRemiseBI = {
  id: 3709,
  num: 30601,
  name: 'square woot correctly',
  posPercent: 23638.24,
  rptGrpNum: 24244,
  rptGrpName: 'mechanically grandiose offend',
  locRef: 'whenever potentially meh',
};

export const sampleWithNewData: NewRemiseBI = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
