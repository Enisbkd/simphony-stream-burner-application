import { IOrderChannel, NewOrderChannel } from './order-channel.model';

export const sampleWithRequiredData: IOrderChannel = {
  id: 14516,
  locRef: 'priesthood whoever',
};

export const sampleWithPartialData: IOrderChannel = {
  id: 30516,
  locRef: 'bank vastly babyish',
  mstrNum: 24606,
};

export const sampleWithFullData: IOrderChannel = {
  id: 32761,
  num: 24567,
  locRef: 'impact',
  name: 'kindheartedly onto abscond',
  mstrNum: 3376,
  mstrName: 'hefty pfft webbed',
};

export const sampleWithNewData: NewOrderChannel = {
  locRef: 'searchingly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
