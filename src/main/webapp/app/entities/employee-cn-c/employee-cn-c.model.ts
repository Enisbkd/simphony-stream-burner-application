export interface IEmployeeCnC {
  id: number;
  objectNum?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  checkName?: string | null;
  email?: string | null;
  languageObjNum?: number | null;
  langId?: number | null;
  alternateId?: number | null;
  level?: number | null;
  group?: number | null;
  userName?: string | null;
  firstRoleHierUnitId?: number | null;
  firstRoleObjNum?: number | null;
  firstVisibilityHierUnitId?: number | null;
  firstVisibilityPropagateToChildren?: boolean | null;
  firstPropertyHierUnitId?: number | null;
  firstPropertyObjNum?: number | null;
  firstPropertyEmpClassObjNum?: number | null;
  firstPropertyOptions?: string | null;
  firstOperatorRvcHierUnitId?: number | null;
  firstOperatorRvcObjNum?: number | null;
  firstOperatorOptions?: string | null;
  firstOperatorCashDrawerObjNum?: number | null;
  firstOperatorTmsColorObjNum?: number | null;
  firstOperatorServerEfficiency?: number | null;
  pin?: string | null;
  accessId?: number | null;
}

export type NewEmployeeCnC = Omit<IEmployeeCnC, 'id'> & { id: null };
