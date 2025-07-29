export interface IMenuItemDefinitionsCnC {
  id: number;
  hierUnitId?: number | null;
  menuItemMasterObjNum?: number | null;
  menuItemMasterId?: number | null;
  menuItemDefinitionId?: number | null;
  defSequenceNum?: number | null;
  menuItemClassObjNum?: number | null;
  overridePrintClassObjNum?: number | null;
  mainLevel?: string | null;
  subLevel?: string | null;
  quantity?: number | null;
  kdsPrepTime?: number | null;
  prefixLevelOverride?: number | null;
  guestCount?: number | null;
  slu1ObjNum?: string | null;
  slu2ObjNum?: string | null;
  slu3ObjNum?: string | null;
  slu4ObjNum?: string | null;
  slu5ObjNum?: string | null;
  slu6ObjNum?: string | null;
  slu7ObjNum?: string | null;
  slu8ObjNum?: string | null;
  firstName?: string | null;
}

export type NewMenuItemDefinitionsCnC = Omit<IMenuItemDefinitionsCnC, 'id'> & { id: null };
