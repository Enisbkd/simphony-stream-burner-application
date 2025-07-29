import { IPointDeVenteTrans, NewPointDeVenteTrans } from './point-de-vente-trans.model';

export const sampleWithRequiredData: IPointDeVenteTrans = {
  id: 32718,
};

export const sampleWithPartialData: IPointDeVenteTrans = {
  id: 21244,
};

export const sampleWithFullData: IPointDeVenteTrans = {
  id: 12235,
  rvcRef: 6426,
  name: 'pointed stable',
  locRef: 'what',
  orgShortName: 'ah bobble',
  address: 'reorganisation',
};

export const sampleWithNewData: NewPointDeVenteTrans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
