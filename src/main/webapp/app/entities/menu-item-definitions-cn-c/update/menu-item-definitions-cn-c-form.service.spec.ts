import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../menu-item-definitions-cn-c.test-samples';

import { MenuItemDefinitionsCnCFormService } from './menu-item-definitions-cn-c-form.service';

describe('MenuItemDefinitionsCnC Form Service', () => {
  let service: MenuItemDefinitionsCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuItemDefinitionsCnCFormService);
  });

  describe('Service methods', () => {
    describe('createMenuItemDefinitionsCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            menuItemMasterObjNum: expect.any(Object),
            menuItemMasterId: expect.any(Object),
            menuItemDefinitionId: expect.any(Object),
            defSequenceNum: expect.any(Object),
            menuItemClassObjNum: expect.any(Object),
            overridePrintClassObjNum: expect.any(Object),
            mainLevel: expect.any(Object),
            subLevel: expect.any(Object),
            quantity: expect.any(Object),
            kdsPrepTime: expect.any(Object),
            prefixLevelOverride: expect.any(Object),
            guestCount: expect.any(Object),
            slu1ObjNum: expect.any(Object),
            slu2ObjNum: expect.any(Object),
            slu3ObjNum: expect.any(Object),
            slu4ObjNum: expect.any(Object),
            slu5ObjNum: expect.any(Object),
            slu6ObjNum: expect.any(Object),
            slu7ObjNum: expect.any(Object),
            slu8ObjNum: expect.any(Object),
            firstName: expect.any(Object),
          }),
        );
      });

      it('passing IMenuItemDefinitionsCnC should create a new form with FormGroup', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            menuItemMasterObjNum: expect.any(Object),
            menuItemMasterId: expect.any(Object),
            menuItemDefinitionId: expect.any(Object),
            defSequenceNum: expect.any(Object),
            menuItemClassObjNum: expect.any(Object),
            overridePrintClassObjNum: expect.any(Object),
            mainLevel: expect.any(Object),
            subLevel: expect.any(Object),
            quantity: expect.any(Object),
            kdsPrepTime: expect.any(Object),
            prefixLevelOverride: expect.any(Object),
            guestCount: expect.any(Object),
            slu1ObjNum: expect.any(Object),
            slu2ObjNum: expect.any(Object),
            slu3ObjNum: expect.any(Object),
            slu4ObjNum: expect.any(Object),
            slu5ObjNum: expect.any(Object),
            slu6ObjNum: expect.any(Object),
            slu7ObjNum: expect.any(Object),
            slu8ObjNum: expect.any(Object),
            firstName: expect.any(Object),
          }),
        );
      });
    });

    describe('getMenuItemDefinitionsCnC', () => {
      it('should return NewMenuItemDefinitionsCnC for default MenuItemDefinitionsCnC initial value', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup(sampleWithNewData);

        const menuItemDefinitionsCnC = service.getMenuItemDefinitionsCnC(formGroup) as any;

        expect(menuItemDefinitionsCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewMenuItemDefinitionsCnC for empty MenuItemDefinitionsCnC initial value', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup();

        const menuItemDefinitionsCnC = service.getMenuItemDefinitionsCnC(formGroup) as any;

        expect(menuItemDefinitionsCnC).toMatchObject({});
      });

      it('should return IMenuItemDefinitionsCnC', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup(sampleWithRequiredData);

        const menuItemDefinitionsCnC = service.getMenuItemDefinitionsCnC(formGroup) as any;

        expect(menuItemDefinitionsCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMenuItemDefinitionsCnC should not enable id FormControl', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMenuItemDefinitionsCnC should disable id FormControl', () => {
        const formGroup = service.createMenuItemDefinitionsCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
