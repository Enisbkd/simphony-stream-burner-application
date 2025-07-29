import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../order-channel-bi.test-samples';

import { OrderChannelBIFormService } from './order-channel-bi-form.service';

describe('OrderChannelBI Form Service', () => {
  let service: OrderChannelBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderChannelBIFormService);
  });

  describe('Service methods', () => {
    describe('createOrderChannelBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrderChannelBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            locRef: expect.any(Object),
            name: expect.any(Object),
            mstrNum: expect.any(Object),
            mstrName: expect.any(Object),
          }),
        );
      });

      it('passing IOrderChannelBI should create a new form with FormGroup', () => {
        const formGroup = service.createOrderChannelBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            locRef: expect.any(Object),
            name: expect.any(Object),
            mstrNum: expect.any(Object),
            mstrName: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrderChannelBI', () => {
      it('should return NewOrderChannelBI for default OrderChannelBI initial value', () => {
        const formGroup = service.createOrderChannelBIFormGroup(sampleWithNewData);

        const orderChannelBI = service.getOrderChannelBI(formGroup) as any;

        expect(orderChannelBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrderChannelBI for empty OrderChannelBI initial value', () => {
        const formGroup = service.createOrderChannelBIFormGroup();

        const orderChannelBI = service.getOrderChannelBI(formGroup) as any;

        expect(orderChannelBI).toMatchObject({});
      });

      it('should return IOrderChannelBI', () => {
        const formGroup = service.createOrderChannelBIFormGroup(sampleWithRequiredData);

        const orderChannelBI = service.getOrderChannelBI(formGroup) as any;

        expect(orderChannelBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrderChannelBI should not enable id FormControl', () => {
        const formGroup = service.createOrderChannelBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrderChannelBI should disable id FormControl', () => {
        const formGroup = service.createOrderChannelBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
