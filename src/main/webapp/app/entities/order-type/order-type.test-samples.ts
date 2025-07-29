import { IOrderType, NewOrderType } from './order-type.model';

export const sampleWithRequiredData: IOrderType = {
  id: 4310,
  locRef: 'once clamor',
};

export const sampleWithPartialData: IOrderType = {
  id: 23286,
  locRef: 'amidst although disconnection',
  name: 'truly',
  mstrNum: 143,
  mstrName: 'disadvantage',
  catGrpHierName1: 'frizzy gripper surprisingly',
  catGrpName1: 'blissfully',
  catGrpHierName2: 'wise merry',
};

export const sampleWithFullData: IOrderType = {
  id: 32332,
  num: 11922,
  locRef: 'bogus',
  name: 'verbally unto vary',
  mstrNum: 18043,
  mstrName: 'besides satirise',
  catGrpHierName1: 'yowza ugh construe',
  catGrpName1: 'yum vacantly enormously',
  catGrpHierName2: 'anti',
  catGrpName2: 'anneal state',
  catGrpHierName3: 'readily',
  catGrpName3: 'who woot tensely',
  catGrpHierName4: 'barring',
  catGrpName4: 'completion airbrush',
};

export const sampleWithNewData: NewOrderType = {
  locRef: 'yahoo wallop',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
