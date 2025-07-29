export interface IMenuItemPricesCnC {
  id: number;
  hierUnitId?: number | null;
  menuItemPriceId?: number | null;
  menuItemMasterId?: number | null;
  menuItemMasterObjNum?: number | null;
  menuItemDefinitionId?: number | null;
  defSequenceNum?: number | null;
  externalReference1?: string | null;
  externalReference2?: string | null;
  priceSequenceNum?: number | null;
  activeOnMenuLevel?: number | null;
  effectivityGroupObjNum?: string | null;
  prepCost?: number | null;
  price?: number | null;
  options?: string | null;
}

export type NewMenuItemPricesCnC = Omit<IMenuItemPricesCnC, 'id'> & { id: null };
