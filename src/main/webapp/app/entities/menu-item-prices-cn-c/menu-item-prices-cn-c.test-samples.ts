import { IMenuItemPricesCnC, NewMenuItemPricesCnC } from './menu-item-prices-cn-c.model';

export const sampleWithRequiredData: IMenuItemPricesCnC = {
  id: 10335,
};

export const sampleWithPartialData: IMenuItemPricesCnC = {
  id: 31857,
  menuItemPriceId: 5888,
  menuItemDefinitionId: 15820,
  externalReference1: 'cruelly',
  externalReference2: 'but simplistic',
  priceSequenceNum: 2960,
  activeOnMenuLevel: 14372,
  price: 24358.97,
  options: 'phooey underneath impressive',
};

export const sampleWithFullData: IMenuItemPricesCnC = {
  id: 8225,
  hierUnitId: 27295,
  menuItemPriceId: 28963,
  menuItemMasterId: 10211,
  menuItemMasterObjNum: 5982,
  menuItemDefinitionId: 2109,
  defSequenceNum: 8588,
  externalReference1: 'shoulder on quaver',
  externalReference2: 'because',
  priceSequenceNum: 15091,
  activeOnMenuLevel: 17327,
  effectivityGroupObjNum: 'energetically readily wherever',
  prepCost: 26209.91,
  price: 20887.77,
  options: 'amongst yahoo quarrelsomely',
};

export const sampleWithNewData: NewMenuItemPricesCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
