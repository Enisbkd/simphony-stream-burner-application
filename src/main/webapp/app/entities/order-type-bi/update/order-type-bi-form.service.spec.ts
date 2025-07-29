import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../order-type-bi.test-samples';

import { OrderTypeBIFormService } from './order-type-bi-form.service';

describe('OrderTypeBI Form Service', () => {
  let service: OrderTypeBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderTypeBIFormService);
  });

  describe('Service methods', () => {
    describe('createOrderTypeBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrderTypeBIFormGroup();

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

      it('passing IOrderTypeBI should create a new form with FormGroup', () => {
        const formGroup = service.createOrderTypeBIFormGroup(sampleWithRequiredData);

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

    describe('getOrderTypeBI', () => {
      it('should return NewOrderTypeBI for default OrderTypeBI initial value', () => {
        const formGroup = service.createOrderTypeBIFormGroup(sampleWithNewData);

        const orderTypeBI = service.getOrderTypeBI(formGroup) as any;

        expect(orderTypeBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrderTypeBI for empty OrderTypeBI initial value', () => {
        const formGroup = service.createOrderTypeBIFormGroup();

        const orderTypeBI = service.getOrderTypeBI(formGroup) as any;

        expect(orderTypeBI).toMatchObject({});
      });

      it('should return IOrderTypeBI', () => {
        const formGroup = service.createOrderTypeBIFormGroup(sampleWithRequiredData);

        const orderTypeBI = service.getOrderTypeBI(formGroup) as any;

        expect(orderTypeBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrderTypeBI should not enable id FormControl', () => {
        const formGroup = service.createOrderTypeBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrderTypeBI should disable id FormControl', () => {
        const formGroup = service.createOrderTypeBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
