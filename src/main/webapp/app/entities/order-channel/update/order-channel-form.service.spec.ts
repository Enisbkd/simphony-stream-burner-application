import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../order-channel.test-samples';

import { OrderChannelFormService } from './order-channel-form.service';

describe('OrderChannel Form Service', () => {
  let service: OrderChannelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderChannelFormService);
  });

  describe('Service methods', () => {
    describe('createOrderChannelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrderChannelFormGroup();

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

      it('passing IOrderChannel should create a new form with FormGroup', () => {
        const formGroup = service.createOrderChannelFormGroup(sampleWithRequiredData);

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

    describe('getOrderChannel', () => {
      it('should return NewOrderChannel for default OrderChannel initial value', () => {
        const formGroup = service.createOrderChannelFormGroup(sampleWithNewData);

        const orderChannel = service.getOrderChannel(formGroup) as any;

        expect(orderChannel).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrderChannel for empty OrderChannel initial value', () => {
        const formGroup = service.createOrderChannelFormGroup();

        const orderChannel = service.getOrderChannel(formGroup) as any;

        expect(orderChannel).toMatchObject({});
      });

      it('should return IOrderChannel', () => {
        const formGroup = service.createOrderChannelFormGroup(sampleWithRequiredData);

        const orderChannel = service.getOrderChannel(formGroup) as any;

        expect(orderChannel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrderChannel should not enable id FormControl', () => {
        const formGroup = service.createOrderChannelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrderChannel should disable id FormControl', () => {
        const formGroup = service.createOrderChannelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
