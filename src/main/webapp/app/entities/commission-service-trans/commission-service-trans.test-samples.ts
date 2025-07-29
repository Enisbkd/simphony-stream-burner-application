import { ICommissionServiceTrans, NewCommissionServiceTrans } from './commission-service-trans.model';

export const sampleWithRequiredData: ICommissionServiceTrans = {
  id: 22782,
};

export const sampleWithPartialData: ICommissionServiceTrans = {
  id: 13060,
  orgShortName: 'um or while',
  rvcRef: 28344,
  type: 'under',
  value: 31503.82,
};

export const sampleWithFullData: ICommissionServiceTrans = {
  id: 31092,
  orgShortName: 'blah',
  locRef: 'phooey continually nor',
  rvcRef: 12129,
  serviceChargeId: 4628,
  name: 'perky pace',
  type: 'concrete',
  value: 24917.03,
};

export const sampleWithNewData: NewCommissionServiceTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
