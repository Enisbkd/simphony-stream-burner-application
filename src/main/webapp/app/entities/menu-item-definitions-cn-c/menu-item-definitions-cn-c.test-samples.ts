import { IMenuItemDefinitionsCnC, NewMenuItemDefinitionsCnC } from './menu-item-definitions-cn-c.model';

export const sampleWithRequiredData: IMenuItemDefinitionsCnC = {
  id: 24723,
};

export const sampleWithPartialData: IMenuItemDefinitionsCnC = {
  id: 22132,
  menuItemMasterId: 318,
  menuItemDefinitionId: 5480,
  menuItemClassObjNum: 6126,
  kdsPrepTime: 20285.34,
  guestCount: 25832,
  slu1ObjNum: 'log snowplow continually',
  slu3ObjNum: 'concerning',
  slu5ObjNum: 'obvious',
  slu8ObjNum: 'with quietly',
  firstName: 'Otho',
};

export const sampleWithFullData: IMenuItemDefinitionsCnC = {
  id: 15231,
  hierUnitId: 1874,
  menuItemMasterObjNum: 3228,
  menuItemMasterId: 32032,
  menuItemDefinitionId: 12587,
  defSequenceNum: 24519,
  menuItemClassObjNum: 12833,
  overridePrintClassObjNum: 9385,
  mainLevel: 'as lanky whether',
  subLevel: 'anguished',
  quantity: 22433,
  kdsPrepTime: 20180.51,
  prefixLevelOverride: 27089,
  guestCount: 29301,
  slu1ObjNum: 'longingly',
  slu2ObjNum: 'properly fashion',
  slu3ObjNum: 'hastily puff',
  slu4ObjNum: 'about',
  slu5ObjNum: 'besides monumental',
  slu6ObjNum: 'boldly and wherever',
  slu7ObjNum: 'regarding far',
  slu8ObjNum: 'phooey',
  firstName: 'Selina',
};

export const sampleWithNewData: NewMenuItemDefinitionsCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
