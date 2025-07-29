import { IPointDeVenteCnC, NewPointDeVenteCnC } from './point-de-vente-cn-c.model';

export const sampleWithRequiredData: IPointDeVenteCnC = {
  id: 32616,
};

export const sampleWithPartialData: IPointDeVenteCnC = {
  id: 27917,
  locHierUnitId: 16241,
  locObjNum: 8419,
  hierUnitId: 4772,
  objectNum: 16665,
  dataExtensions: 'questionably pfft subdued',
};

export const sampleWithFullData: IPointDeVenteCnC = {
  id: 22692,
  locHierUnitId: 22316,
  locObjNum: 6611,
  rvcId: 18460,
  kdsControllerId: 19704,
  hierUnitId: 28402,
  objectNum: 20839,
  name: 'handsome whose yahoo',
  dataExtensions: 'tune fooey oh',
};

export const sampleWithNewData: NewPointDeVenteCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
