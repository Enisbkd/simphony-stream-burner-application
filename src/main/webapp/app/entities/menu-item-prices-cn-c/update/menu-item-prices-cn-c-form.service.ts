import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMenuItemPricesCnC, NewMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMenuItemPricesCnC for edit and NewMenuItemPricesCnCFormGroupInput for create.
 */
type MenuItemPricesCnCFormGroupInput = IMenuItemPricesCnC | PartialWithRequiredKeyOf<NewMenuItemPricesCnC>;

type MenuItemPricesCnCFormDefaults = Pick<NewMenuItemPricesCnC, 'id'>;

type MenuItemPricesCnCFormGroupContent = {
  id: FormControl<IMenuItemPricesCnC['id'] | NewMenuItemPricesCnC['id']>;
  hierUnitId: FormControl<IMenuItemPricesCnC['hierUnitId']>;
  menuItemPriceId: FormControl<IMenuItemPricesCnC['menuItemPriceId']>;
  menuItemMasterId: FormControl<IMenuItemPricesCnC['menuItemMasterId']>;
  menuItemMasterObjNum: FormControl<IMenuItemPricesCnC['menuItemMasterObjNum']>;
  menuItemDefinitionId: FormControl<IMenuItemPricesCnC['menuItemDefinitionId']>;
  defSequenceNum: FormControl<IMenuItemPricesCnC['defSequenceNum']>;
  externalReference1: FormControl<IMenuItemPricesCnC['externalReference1']>;
  externalReference2: FormControl<IMenuItemPricesCnC['externalReference2']>;
  priceSequenceNum: FormControl<IMenuItemPricesCnC['priceSequenceNum']>;
  activeOnMenuLevel: FormControl<IMenuItemPricesCnC['activeOnMenuLevel']>;
  effectivityGroupObjNum: FormControl<IMenuItemPricesCnC['effectivityGroupObjNum']>;
  prepCost: FormControl<IMenuItemPricesCnC['prepCost']>;
  price: FormControl<IMenuItemPricesCnC['price']>;
  options: FormControl<IMenuItemPricesCnC['options']>;
};

export type MenuItemPricesCnCFormGroup = FormGroup<MenuItemPricesCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MenuItemPricesCnCFormService {
  createMenuItemPricesCnCFormGroup(menuItemPricesCnC: MenuItemPricesCnCFormGroupInput = { id: null }): MenuItemPricesCnCFormGroup {
    const menuItemPricesCnCRawValue = {
      ...this.getFormDefaults(),
      ...menuItemPricesCnC,
    };
    return new FormGroup<MenuItemPricesCnCFormGroupContent>({
      id: new FormControl(
        { value: menuItemPricesCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      hierUnitId: new FormControl(menuItemPricesCnCRawValue.hierUnitId),
      menuItemPriceId: new FormControl(menuItemPricesCnCRawValue.menuItemPriceId),
      menuItemMasterId: new FormControl(menuItemPricesCnCRawValue.menuItemMasterId),
      menuItemMasterObjNum: new FormControl(menuItemPricesCnCRawValue.menuItemMasterObjNum),
      menuItemDefinitionId: new FormControl(menuItemPricesCnCRawValue.menuItemDefinitionId),
      defSequenceNum: new FormControl(menuItemPricesCnCRawValue.defSequenceNum),
      externalReference1: new FormControl(menuItemPricesCnCRawValue.externalReference1),
      externalReference2: new FormControl(menuItemPricesCnCRawValue.externalReference2),
      priceSequenceNum: new FormControl(menuItemPricesCnCRawValue.priceSequenceNum),
      activeOnMenuLevel: new FormControl(menuItemPricesCnCRawValue.activeOnMenuLevel),
      effectivityGroupObjNum: new FormControl(menuItemPricesCnCRawValue.effectivityGroupObjNum),
      prepCost: new FormControl(menuItemPricesCnCRawValue.prepCost),
      price: new FormControl(menuItemPricesCnCRawValue.price),
      options: new FormControl(menuItemPricesCnCRawValue.options),
    });
  }

  getMenuItemPricesCnC(form: MenuItemPricesCnCFormGroup): IMenuItemPricesCnC | NewMenuItemPricesCnC {
    return form.getRawValue() as IMenuItemPricesCnC | NewMenuItemPricesCnC;
  }

  resetForm(form: MenuItemPricesCnCFormGroup, menuItemPricesCnC: MenuItemPricesCnCFormGroupInput): void {
    const menuItemPricesCnCRawValue = { ...this.getFormDefaults(), ...menuItemPricesCnC };
    form.reset(
      {
        ...menuItemPricesCnCRawValue,
        id: { value: menuItemPricesCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MenuItemPricesCnCFormDefaults {
    return {
      id: null,
    };
  }
}
