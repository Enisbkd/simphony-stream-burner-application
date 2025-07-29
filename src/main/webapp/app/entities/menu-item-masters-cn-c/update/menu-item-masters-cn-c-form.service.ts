import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMenuItemMastersCnC, NewMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMenuItemMastersCnC for edit and NewMenuItemMastersCnCFormGroupInput for create.
 */
type MenuItemMastersCnCFormGroupInput = IMenuItemMastersCnC | PartialWithRequiredKeyOf<NewMenuItemMastersCnC>;

type MenuItemMastersCnCFormDefaults = Pick<NewMenuItemMastersCnC, 'id'>;

type MenuItemMastersCnCFormGroupContent = {
  id: FormControl<IMenuItemMastersCnC['id'] | NewMenuItemMastersCnC['id']>;
  hierUnitId: FormControl<IMenuItemMastersCnC['hierUnitId']>;
  menuItemMasterId: FormControl<IMenuItemMastersCnC['menuItemMasterId']>;
  familyGroupObjectNum: FormControl<IMenuItemMastersCnC['familyGroupObjectNum']>;
  majorGroupObjectNum: FormControl<IMenuItemMastersCnC['majorGroupObjectNum']>;
  reportGroupObjectNum: FormControl<IMenuItemMastersCnC['reportGroupObjectNum']>;
  externalReference1: FormControl<IMenuItemMastersCnC['externalReference1']>;
  externalReference2: FormControl<IMenuItemMastersCnC['externalReference2']>;
  objectNum: FormControl<IMenuItemMastersCnC['objectNum']>;
  name: FormControl<IMenuItemMastersCnC['name']>;
};

export type MenuItemMastersCnCFormGroup = FormGroup<MenuItemMastersCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MenuItemMastersCnCFormService {
  createMenuItemMastersCnCFormGroup(menuItemMastersCnC: MenuItemMastersCnCFormGroupInput = { id: null }): MenuItemMastersCnCFormGroup {
    const menuItemMastersCnCRawValue = {
      ...this.getFormDefaults(),
      ...menuItemMastersCnC,
    };
    return new FormGroup<MenuItemMastersCnCFormGroupContent>({
      id: new FormControl(
        { value: menuItemMastersCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      hierUnitId: new FormControl(menuItemMastersCnCRawValue.hierUnitId),
      menuItemMasterId: new FormControl(menuItemMastersCnCRawValue.menuItemMasterId),
      familyGroupObjectNum: new FormControl(menuItemMastersCnCRawValue.familyGroupObjectNum),
      majorGroupObjectNum: new FormControl(menuItemMastersCnCRawValue.majorGroupObjectNum),
      reportGroupObjectNum: new FormControl(menuItemMastersCnCRawValue.reportGroupObjectNum),
      externalReference1: new FormControl(menuItemMastersCnCRawValue.externalReference1),
      externalReference2: new FormControl(menuItemMastersCnCRawValue.externalReference2),
      objectNum: new FormControl(menuItemMastersCnCRawValue.objectNum),
      name: new FormControl(menuItemMastersCnCRawValue.name),
    });
  }

  getMenuItemMastersCnC(form: MenuItemMastersCnCFormGroup): IMenuItemMastersCnC | NewMenuItemMastersCnC {
    return form.getRawValue() as IMenuItemMastersCnC | NewMenuItemMastersCnC;
  }

  resetForm(form: MenuItemMastersCnCFormGroup, menuItemMastersCnC: MenuItemMastersCnCFormGroupInput): void {
    const menuItemMastersCnCRawValue = { ...this.getFormDefaults(), ...menuItemMastersCnC };
    form.reset(
      {
        ...menuItemMastersCnCRawValue,
        id: { value: menuItemMastersCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MenuItemMastersCnCFormDefaults {
    return {
      id: null,
    };
  }
}
