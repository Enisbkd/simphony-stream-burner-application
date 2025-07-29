import { IMenuItemMastersCnC, NewMenuItemMastersCnC } from './menu-item-masters-cn-c.model';

export const sampleWithRequiredData: IMenuItemMastersCnC = {
  id: 27021,
};

export const sampleWithPartialData: IMenuItemMastersCnC = {
  id: 30514,
  hierUnitId: 28035,
  externalReference2: 'as',
  objectNum: 15452,
};

export const sampleWithFullData: IMenuItemMastersCnC = {
  id: 853,
  hierUnitId: 1039,
  menuItemMasterId: 13503,
  familyGroupObjectNum: 16703,
  majorGroupObjectNum: 1743,
  reportGroupObjectNum: 21443,
  externalReference1: 'pant',
  externalReference2: 'beard',
  objectNum: 3452,
  name: 'scarily schematise',
};

export const sampleWithNewData: NewMenuItemMastersCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
