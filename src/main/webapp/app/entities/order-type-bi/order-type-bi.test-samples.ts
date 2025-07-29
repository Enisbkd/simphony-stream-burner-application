import { IOrderTypeBI, NewOrderTypeBI } from './order-type-bi.model';

export const sampleWithRequiredData: IOrderTypeBI = {
  id: 17307,
  locRef: 'however',
};

export const sampleWithPartialData: IOrderTypeBI = {
  id: 3902,
  num: 19492,
  locRef: 'pasta indeed consequently',
  name: 'since necklace celsius',
  catGrpName1: 'tenement sticker',
  catGrpName2: 'inasmuch',
  catGrpHierName4: 'edible',
};

export const sampleWithFullData: IOrderTypeBI = {
  id: 31789,
  num: 431,
  locRef: 'connect lively',
  name: 'cultivated altruistic conjecture',
  mstrNum: 29478,
  mstrName: 'issue handsome anxiously',
  catGrpHierName1: 'considering wisecrack fashion',
  catGrpName1: 'status tectonics outnumber',
  catGrpHierName2: 'yum inasmuch metallic',
  catGrpName2: 'tenderly',
  catGrpHierName3: 'for ha',
  catGrpName3: 'overfeed overvalue aside',
  catGrpHierName4: 'sparkling cram costume',
  catGrpName4: 'dazzling since',
};

export const sampleWithNewData: NewOrderTypeBI = {
  locRef: 'treasure aha',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
