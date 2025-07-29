import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../point-de-vente-trans.test-samples';

import { PointDeVenteTransFormService } from './point-de-vente-trans-form.service';

describe('PointDeVenteTrans Form Service', () => {
  let service: PointDeVenteTransFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PointDeVenteTransFormService);
  });

  describe('Service methods', () => {
    describe('createPointDeVenteTransFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPointDeVenteTransFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rvcRef: expect.any(Object),
            name: expect.any(Object),
            locRef: expect.any(Object),
            orgShortName: expect.any(Object),
            address: expect.any(Object),
          }),
        );
      });

      it('passing IPointDeVenteTrans should create a new form with FormGroup', () => {
        const formGroup = service.createPointDeVenteTransFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rvcRef: expect.any(Object),
            name: expect.any(Object),
            locRef: expect.any(Object),
            orgShortName: expect.any(Object),
            address: expect.any(Object),
          }),
        );
      });
    });

    describe('getPointDeVenteTrans', () => {
      it('should return NewPointDeVenteTrans for default PointDeVenteTrans initial value', () => {
        const formGroup = service.createPointDeVenteTransFormGroup(sampleWithNewData);

        const pointDeVenteTrans = service.getPointDeVenteTrans(formGroup) as any;

        expect(pointDeVenteTrans).toMatchObject(sampleWithNewData);
      });

      it('should return NewPointDeVenteTrans for empty PointDeVenteTrans initial value', () => {
        const formGroup = service.createPointDeVenteTransFormGroup();

        const pointDeVenteTrans = service.getPointDeVenteTrans(formGroup) as any;

        expect(pointDeVenteTrans).toMatchObject({});
      });

      it('should return IPointDeVenteTrans', () => {
        const formGroup = service.createPointDeVenteTransFormGroup(sampleWithRequiredData);

        const pointDeVenteTrans = service.getPointDeVenteTrans(formGroup) as any;

        expect(pointDeVenteTrans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPointDeVenteTrans should not enable id FormControl', () => {
        const formGroup = service.createPointDeVenteTransFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPointDeVenteTrans should disable id FormControl', () => {
        const formGroup = service.createPointDeVenteTransFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
