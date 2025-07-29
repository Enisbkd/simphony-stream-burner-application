import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMenuItemDefinitionsCnC, NewMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMenuItemDefinitionsCnC for edit and NewMenuItemDefinitionsCnCFormGroupInput for create.
 */
type MenuItemDefinitionsCnCFormGroupInput = IMenuItemDefinitionsCnC | PartialWithRequiredKeyOf<NewMenuItemDefinitionsCnC>;

type MenuItemDefinitionsCnCFormDefaults = Pick<NewMenuItemDefinitionsCnC, 'id'>;

type MenuItemDefinitionsCnCFormGroupContent = {
  id: FormControl<IMenuItemDefinitionsCnC['id'] | NewMenuItemDefinitionsCnC['id']>;
  hierUnitId: FormControl<IMenuItemDefinitionsCnC['hierUnitId']>;
  menuItemMasterObjNum: FormControl<IMenuItemDefinitionsCnC['menuItemMasterObjNum']>;
  menuItemMasterId: FormControl<IMenuItemDefinitionsCnC['menuItemMasterId']>;
  menuItemDefinitionId: FormControl<IMenuItemDefinitionsCnC['menuItemDefinitionId']>;
  defSequenceNum: FormControl<IMenuItemDefinitionsCnC['defSequenceNum']>;
  menuItemClassObjNum: FormControl<IMenuItemDefinitionsCnC['menuItemClassObjNum']>;
  overridePrintClassObjNum: FormControl<IMenuItemDefinitionsCnC['overridePrintClassObjNum']>;
  mainLevel: FormControl<IMenuItemDefinitionsCnC['mainLevel']>;
  subLevel: FormControl<IMenuItemDefinitionsCnC['subLevel']>;
  quantity: FormControl<IMenuItemDefinitionsCnC['quantity']>;
  kdsPrepTime: FormControl<IMenuItemDefinitionsCnC['kdsPrepTime']>;
  prefixLevelOverride: FormControl<IMenuItemDefinitionsCnC['prefixLevelOverride']>;
  guestCount: FormControl<IMenuItemDefinitionsCnC['guestCount']>;
  slu1ObjNum: FormControl<IMenuItemDefinitionsCnC['slu1ObjNum']>;
  slu2ObjNum: FormControl<IMenuItemDefinitionsCnC['slu2ObjNum']>;
  slu3ObjNum: FormControl<IMenuItemDefinitionsCnC['slu3ObjNum']>;
  slu4ObjNum: FormControl<IMenuItemDefinitionsCnC['slu4ObjNum']>;
  slu5ObjNum: FormControl<IMenuItemDefinitionsCnC['slu5ObjNum']>;
  slu6ObjNum: FormControl<IMenuItemDefinitionsCnC['slu6ObjNum']>;
  slu7ObjNum: FormControl<IMenuItemDefinitionsCnC['slu7ObjNum']>;
  slu8ObjNum: FormControl<IMenuItemDefinitionsCnC['slu8ObjNum']>;
  firstName: FormControl<IMenuItemDefinitionsCnC['firstName']>;
};

export type MenuItemDefinitionsCnCFormGroup = FormGroup<MenuItemDefinitionsCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MenuItemDefinitionsCnCFormService {
  createMenuItemDefinitionsCnCFormGroup(
    menuItemDefinitionsCnC: MenuItemDefinitionsCnCFormGroupInput = { id: null },
  ): MenuItemDefinitionsCnCFormGroup {
    const menuItemDefinitionsCnCRawValue = {
      ...this.getFormDefaults(),
      ...menuItemDefinitionsCnC,
    };
    return new FormGroup<MenuItemDefinitionsCnCFormGroupContent>({
      id: new FormControl(
        { value: menuItemDefinitionsCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      hierUnitId: new FormControl(menuItemDefinitionsCnCRawValue.hierUnitId),
      menuItemMasterObjNum: new FormControl(menuItemDefinitionsCnCRawValue.menuItemMasterObjNum),
      menuItemMasterId: new FormControl(menuItemDefinitionsCnCRawValue.menuItemMasterId),
      menuItemDefinitionId: new FormControl(menuItemDefinitionsCnCRawValue.menuItemDefinitionId),
      defSequenceNum: new FormControl(menuItemDefinitionsCnCRawValue.defSequenceNum),
      menuItemClassObjNum: new FormControl(menuItemDefinitionsCnCRawValue.menuItemClassObjNum),
      overridePrintClassObjNum: new FormControl(menuItemDefinitionsCnCRawValue.overridePrintClassObjNum),
      mainLevel: new FormControl(menuItemDefinitionsCnCRawValue.mainLevel),
      subLevel: new FormControl(menuItemDefinitionsCnCRawValue.subLevel),
      quantity: new FormControl(menuItemDefinitionsCnCRawValue.quantity),
      kdsPrepTime: new FormControl(menuItemDefinitionsCnCRawValue.kdsPrepTime),
      prefixLevelOverride: new FormControl(menuItemDefinitionsCnCRawValue.prefixLevelOverride),
      guestCount: new FormControl(menuItemDefinitionsCnCRawValue.guestCount),
      slu1ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu1ObjNum),
      slu2ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu2ObjNum),
      slu3ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu3ObjNum),
      slu4ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu4ObjNum),
      slu5ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu5ObjNum),
      slu6ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu6ObjNum),
      slu7ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu7ObjNum),
      slu8ObjNum: new FormControl(menuItemDefinitionsCnCRawValue.slu8ObjNum),
      firstName: new FormControl(menuItemDefinitionsCnCRawValue.firstName),
    });
  }

  getMenuItemDefinitionsCnC(form: MenuItemDefinitionsCnCFormGroup): IMenuItemDefinitionsCnC | NewMenuItemDefinitionsCnC {
    return form.getRawValue() as IMenuItemDefinitionsCnC | NewMenuItemDefinitionsCnC;
  }

  resetForm(form: MenuItemDefinitionsCnCFormGroup, menuItemDefinitionsCnC: MenuItemDefinitionsCnCFormGroupInput): void {
    const menuItemDefinitionsCnCRawValue = { ...this.getFormDefaults(), ...menuItemDefinitionsCnC };
    form.reset(
      {
        ...menuItemDefinitionsCnCRawValue,
        id: { value: menuItemDefinitionsCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MenuItemDefinitionsCnCFormDefaults {
    return {
      id: null,
    };
  }
}
