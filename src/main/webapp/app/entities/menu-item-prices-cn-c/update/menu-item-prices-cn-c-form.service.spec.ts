import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../menu-item-prices-cn-c.test-samples';

import { MenuItemPricesCnCFormService } from './menu-item-prices-cn-c-form.service';

describe('MenuItemPricesCnC Form Service', () => {
  let service: MenuItemPricesCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuItemPricesCnCFormService);
  });

  describe('Service methods', () => {
    describe('createMenuItemPricesCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            menuItemPriceId: expect.any(Object),
            menuItemMasterId: expect.any(Object),
            menuItemMasterObjNum: expect.any(Object),
            menuItemDefinitionId: expect.any(Object),
            defSequenceNum: expect.any(Object),
            externalReference1: expect.any(Object),
            externalReference2: expect.any(Object),
            priceSequenceNum: expect.any(Object),
            activeOnMenuLevel: expect.any(Object),
            effectivityGroupObjNum: expect.any(Object),
            prepCost: expect.any(Object),
            price: expect.any(Object),
            options: expect.any(Object),
          }),
        );
      });

      it('passing IMenuItemPricesCnC should create a new form with FormGroup', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hierUnitId: expect.any(Object),
            menuItemPriceId: expect.any(Object),
            menuItemMasterId: expect.any(Object),
            menuItemMasterObjNum: expect.any(Object),
            menuItemDefinitionId: expect.any(Object),
            defSequenceNum: expect.any(Object),
            externalReference1: expect.any(Object),
            externalReference2: expect.any(Object),
            priceSequenceNum: expect.any(Object),
            activeOnMenuLevel: expect.any(Object),
            effectivityGroupObjNum: expect.any(Object),
            prepCost: expect.any(Object),
            price: expect.any(Object),
            options: expect.any(Object),
          }),
        );
      });
    });

    describe('getMenuItemPricesCnC', () => {
      it('should return NewMenuItemPricesCnC for default MenuItemPricesCnC initial value', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup(sampleWithNewData);

        const menuItemPricesCnC = service.getMenuItemPricesCnC(formGroup) as any;

        expect(menuItemPricesCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewMenuItemPricesCnC for empty MenuItemPricesCnC initial value', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup();

        const menuItemPricesCnC = service.getMenuItemPricesCnC(formGroup) as any;

        expect(menuItemPricesCnC).toMatchObject({});
      });

      it('should return IMenuItemPricesCnC', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup(sampleWithRequiredData);

        const menuItemPricesCnC = service.getMenuItemPricesCnC(formGroup) as any;

        expect(menuItemPricesCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMenuItemPricesCnC should not enable id FormControl', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMenuItemPricesCnC should disable id FormControl', () => {
        const formGroup = service.createMenuItemPricesCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
