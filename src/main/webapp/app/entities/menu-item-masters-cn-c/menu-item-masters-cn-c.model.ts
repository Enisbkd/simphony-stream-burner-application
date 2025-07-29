export interface IMenuItemMastersCnC {
  id: number;
  hierUnitId?: number | null;
  menuItemMasterId?: number | null;
  familyGroupObjectNum?: number | null;
  majorGroupObjectNum?: number | null;
  reportGroupObjectNum?: number | null;
  externalReference1?: string | null;
  externalReference2?: string | null;
  objectNum?: number | null;
  name?: string | null;
}

export type NewMenuItemMastersCnC = Omit<IMenuItemMastersCnC, 'id'> & { id: null };
