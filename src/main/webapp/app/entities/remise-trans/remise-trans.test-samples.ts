import { IRemiseTrans, NewRemiseTrans } from './remise-trans.model';

export const sampleWithRequiredData: IRemiseTrans = {
  id: 30473,
};

export const sampleWithPartialData: IRemiseTrans = {
  id: 21239,
  rvcRef: 23839,
  discountId: 10578,
  engName: 'splay curse',
  discountValue: 9241.88,
};

export const sampleWithFullData: IRemiseTrans = {
  id: 26656,
  orgShortName: 'provided serialize oh',
  locRef: 'kindheartedly relative of',
  rvcRef: 12446,
  discountId: 8471,
  frName: 'helplessly brr even',
  engName: 'readily festival sparse',
  discountType: 'gape',
  discountValue: 14912.01,
};

export const sampleWithNewData: NewRemiseTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
