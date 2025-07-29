import { IEmployeeCnC, NewEmployeeCnC } from './employee-cn-c.model';

export const sampleWithRequiredData: IEmployeeCnC = {
  id: 7121,
};

export const sampleWithPartialData: IEmployeeCnC = {
  id: 7067,
  firstName: 'Tomas',
  lastName: 'Bartoletti',
  checkName: 'small immediate',
  langId: 1174,
  alternateId: 1270,
  firstRoleObjNum: 17724,
  firstVisibilityPropagateToChildren: false,
  firstPropertyHierUnitId: 16485,
  firstPropertyOptions: 'gladly account',
  firstOperatorServerEfficiency: 29093,
  pin: 'minor aware',
};

export const sampleWithFullData: IEmployeeCnC = {
  id: 32026,
  objectNum: 8201,
  firstName: 'Deion',
  lastName: 'Metz',
  checkName: 'but slime',
  email: 'Audrey_Turner92@gmail.com',
  languageObjNum: 29755,
  langId: 1327,
  alternateId: 29326,
  level: 15379,
  group: 8095,
  userName: 'strait boohoo',
  firstRoleHierUnitId: 9235,
  firstRoleObjNum: 11429,
  firstVisibilityHierUnitId: 27586,
  firstVisibilityPropagateToChildren: false,
  firstPropertyHierUnitId: 10198,
  firstPropertyObjNum: 29517,
  firstPropertyEmpClassObjNum: 22022,
  firstPropertyOptions: 'founder cellar too',
  firstOperatorRvcHierUnitId: 22318,
  firstOperatorRvcObjNum: 1602,
  firstOperatorOptions: 'barring rapidly',
  firstOperatorCashDrawerObjNum: 15986,
  firstOperatorTmsColorObjNum: 13803,
  firstOperatorServerEfficiency: 10939,
  pin: 'irritably publicity',
  accessId: 7765,
};

export const sampleWithNewData: NewEmployeeCnC = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
