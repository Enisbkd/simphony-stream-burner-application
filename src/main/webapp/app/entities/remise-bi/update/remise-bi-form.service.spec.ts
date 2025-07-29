import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../remise-bi.test-samples';

import { RemiseBIFormService } from './remise-bi-form.service';

describe('RemiseBI Form Service', () => {
  let service: RemiseBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RemiseBIFormService);
  });

  describe('Service methods', () => {
    describe('createRemiseBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRemiseBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            name: expect.any(Object),
            posPercent: expect.any(Object),
            rptGrpNum: expect.any(Object),
            rptGrpName: expect.any(Object),
            locRef: expect.any(Object),
          }),
        );
      });

      it('passing IRemiseBI should create a new form with FormGroup', () => {
        const formGroup = service.createRemiseBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            name: expect.any(Object),
            posPercent: expect.any(Object),
            rptGrpNum: expect.any(Object),
            rptGrpName: expect.any(Object),
            locRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getRemiseBI', () => {
      it('should return NewRemiseBI for default RemiseBI initial value', () => {
        const formGroup = service.createRemiseBIFormGroup(sampleWithNewData);

        const remiseBI = service.getRemiseBI(formGroup) as any;

        expect(remiseBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewRemiseBI for empty RemiseBI initial value', () => {
        const formGroup = service.createRemiseBIFormGroup();

        const remiseBI = service.getRemiseBI(formGroup) as any;

        expect(remiseBI).toMatchObject({});
      });

      it('should return IRemiseBI', () => {
        const formGroup = service.createRemiseBIFormGroup(sampleWithRequiredData);

        const remiseBI = service.getRemiseBI(formGroup) as any;

        expect(remiseBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRemiseBI should not enable id FormControl', () => {
        const formGroup = service.createRemiseBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRemiseBI should disable id FormControl', () => {
        const formGroup = service.createRemiseBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
