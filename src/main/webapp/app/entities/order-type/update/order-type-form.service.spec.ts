import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../order-type.test-samples';

import { OrderTypeFormService } from './order-type-form.service';

describe('OrderType Form Service', () => {
  let service: OrderTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderTypeFormService);
  });

  describe('Service methods', () => {
    describe('createOrderTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrderTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            locRef: expect.any(Object),
            name: expect.any(Object),
            mstrNum: expect.any(Object),
            mstrName: expect.any(Object),
            catGrpHierName1: expect.any(Object),
            catGrpName1: expect.any(Object),
            catGrpHierName2: expect.any(Object),
            catGrpName2: expect.any(Object),
            catGrpHierName3: expect.any(Object),
            catGrpName3: expect.any(Object),
            catGrpHierName4: expect.any(Object),
            catGrpName4: expect.any(Object),
          }),
        );
      });

      it('passing IOrderType should create a new form with FormGroup', () => {
        const formGroup = service.createOrderTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            locRef: expect.any(Object),
            name: expect.any(Object),
            mstrNum: expect.any(Object),
            mstrName: expect.any(Object),
            catGrpHierName1: expect.any(Object),
            catGrpName1: expect.any(Object),
            catGrpHierName2: expect.any(Object),
            catGrpName2: expect.any(Object),
            catGrpHierName3: expect.any(Object),
            catGrpName3: expect.any(Object),
            catGrpHierName4: expect.any(Object),
            catGrpName4: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrderType', () => {
      it('should return NewOrderType for default OrderType initial value', () => {
        const formGroup = service.createOrderTypeFormGroup(sampleWithNewData);

        const orderType = service.getOrderType(formGroup) as any;

        expect(orderType).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrderType for empty OrderType initial value', () => {
        const formGroup = service.createOrderTypeFormGroup();

        const orderType = service.getOrderType(formGroup) as any;

        expect(orderType).toMatchObject({});
      });

      it('should return IOrderType', () => {
        const formGroup = service.createOrderTypeFormGroup(sampleWithRequiredData);

        const orderType = service.getOrderType(formGroup) as any;

        expect(orderType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrderType should not enable id FormControl', () => {
        const formGroup = service.createOrderTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrderType should disable id FormControl', () => {
        const formGroup = service.createOrderTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
