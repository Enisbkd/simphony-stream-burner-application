import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../menu-item-masters-cn-c.test-samples';

import { MenuItemMastersCnCFormService } from './menu-item-masters-cn-c-form.service';

describe('MenuItemMastersCnC Form Service', () => {
  let service: MenuItemMastersCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuItemMastersCnCFormService);
  });

  describe('Service methods', () => {
    describe('createMenuItemMastersCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            menuItemMasterId: expect.any(Object),
            familyGroupObjectNum: expect.any(Object),
            majorGroupObjectNum: expect.any(Object),
            reportGroupObjectNum: expect.any(Object),
            externalReference1: expect.any(Object),
            externalReference2: expect.any(Object),
            objectNum: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IMenuItemMastersCnC should create a new form with FormGroup', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            menuItemMasterId: expect.any(Object),
            familyGroupObjectNum: expect.any(Object),
            majorGroupObjectNum: expect.any(Object),
            reportGroupObjectNum: expect.any(Object),
            externalReference1: expect.any(Object),
            externalReference2: expect.any(Object),
            objectNum: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getMenuItemMastersCnC', () => {
      it('should return NewMenuItemMastersCnC for default MenuItemMastersCnC initial value', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup(sampleWithNewData);

        const menuItemMastersCnC = service.getMenuItemMastersCnC(formGroup) as any;

        expect(menuItemMastersCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewMenuItemMastersCnC for empty MenuItemMastersCnC initial value', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup();

        const menuItemMastersCnC = service.getMenuItemMastersCnC(formGroup) as any;

        expect(menuItemMastersCnC).toMatchObject({});
      });

      it('should return IMenuItemMastersCnC', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup(sampleWithRequiredData);

        const menuItemMastersCnC = service.getMenuItemMastersCnC(formGroup) as any;

        expect(menuItemMastersCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMenuItemMastersCnC should not enable id FormControl', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMenuItemMastersCnC should disable id FormControl', () => {
        const formGroup = service.createMenuItemMastersCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
