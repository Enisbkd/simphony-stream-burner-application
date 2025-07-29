import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../point-de-vente-cn-c.test-samples';

import { PointDeVenteCnCFormService } from './point-de-vente-cn-c-form.service';

describe('PointDeVenteCnC Form Service', () => {
  let service: PointDeVenteCnCFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PointDeVenteCnCFormService);
  });

  describe('Service methods', () => {
    describe('createPointDeVenteCnCFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locHierUnitId: expect.any(Object),
            locObjNum: expect.any(Object),
            rvcId: expect.any(Object),
            kdsControllerId: expect.any(Object),
            hierUnitId: expect.any(Object),
            objectNum: expect.any(Object),
            name: expect.any(Object),
            dataExtensions: expect.any(Object),
          }),
        );
      });

      it('passing IPointDeVenteCnC should create a new form with FormGroup', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locHierUnitId: expect.any(Object),
            locObjNum: expect.any(Object),
            rvcId: expect.any(Object),
            kdsControllerId: expect.any(Object),
            hierUnitId: expect.any(Object),
            objectNum: expect.any(Object),
            name: expect.any(Object),
            dataExtensions: expect.any(Object),
          }),
        );
      });
    });

    describe('getPointDeVenteCnC', () => {
      it('should return NewPointDeVenteCnC for default PointDeVenteCnC initial value', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup(sampleWithNewData);

        const pointDeVenteCnC = service.getPointDeVenteCnC(formGroup) as any;

        expect(pointDeVenteCnC).toMatchObject(sampleWithNewData);
      });

      it('should return NewPointDeVenteCnC for empty PointDeVenteCnC initial value', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup();

        const pointDeVenteCnC = service.getPointDeVenteCnC(formGroup) as any;

        expect(pointDeVenteCnC).toMatchObject({});
      });

      it('should return IPointDeVenteCnC', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup(sampleWithRequiredData);

        const pointDeVenteCnC = service.getPointDeVenteCnC(formGroup) as any;

        expect(pointDeVenteCnC).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPointDeVenteCnC should not enable id FormControl', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPointDeVenteCnC should disable id FormControl', () => {
        const formGroup = service.createPointDeVenteCnCFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
