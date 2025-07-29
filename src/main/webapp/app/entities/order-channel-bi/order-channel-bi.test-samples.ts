import { IOrderChannelBI, NewOrderChannelBI } from './order-channel-bi.model';

export const sampleWithRequiredData: IOrderChannelBI = {
  id: 26970,
  locRef: 'woot',
};

export const sampleWithPartialData: IOrderChannelBI = {
  id: 18918,
  num: 19516,
  locRef: 'excepting',
};

export const sampleWithFullData: IOrderChannelBI = {
  id: 29356,
  num: 15546,
  locRef: 'unlike',
  name: 'repeatedly',
  mstrNum: 7377,
  mstrName: 'consequently brightly mostly',
};

export const sampleWithNewData: NewOrderChannelBI = {
  locRef: 'rigid yuck',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
