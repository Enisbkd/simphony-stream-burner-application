import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../detail-line-bi.test-samples';

import { DetailLineBIFormService } from './detail-line-bi-form.service';

describe('DetailLineBI Form Service', () => {
  let service: DetailLineBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetailLineBIFormService);
  });

  describe('Service methods', () => {
    describe('createDetailLineBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDetailLineBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            guestCheckLineItemId: expect.any(Object),
            detailUTC: expect.any(Object),
            detailLcl: expect.any(Object),
            seatNum: expect.any(Object),
            prcLvl: expect.any(Object),
            dspTtl: expect.any(Object),
            dspQty: expect.any(Object),
            errCorFlag: expect.any(Object),
            vdFlag: expect.any(Object),
            returnFlag: expect.any(Object),
            doNotShowFlag: expect.any(Object),
            aggTtl: expect.any(Object),
            rsnCodeNum: expect.any(Object),
            refInfo1: expect.any(Object),
            refInfo2: expect.any(Object),
            svcRndNum: expect.any(Object),
            parDtlId: expect.any(Object),
            chkEmpId: expect.any(Object),
            transEmpNum: expect.any(Object),
            mgrEmpNum: expect.any(Object),
            mealEmpNum: expect.any(Object),
            dscNum: expect.any(Object),
            dscMiNum: expect.any(Object),
            svcChgNum: expect.any(Object),
            tmedNum: expect.any(Object),
            miNum: expect.any(Object),
            guestCheckBI: expect.any(Object),
          }),
        );
      });

      it('passing IDetailLineBI should create a new form with FormGroup', () => {
        const formGroup = service.createDetailLineBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            guestCheckLineItemId: expect.any(Object),
            detailUTC: expect.any(Object),
            detailLcl: expect.any(Object),
            seatNum: expect.any(Object),
            prcLvl: expect.any(Object),
            dspTtl: expect.any(Object),
            dspQty: expect.any(Object),
            errCorFlag: expect.any(Object),
            vdFlag: expect.any(Object),
            returnFlag: expect.any(Object),
            doNotShowFlag: expect.any(Object),
            aggTtl: expect.any(Object),
            rsnCodeNum: expect.any(Object),
            refInfo1: expect.any(Object),
            refInfo2: expect.any(Object),
            svcRndNum: expect.any(Object),
            parDtlId: expect.any(Object),
            chkEmpId: expect.any(Object),
            transEmpNum: expect.any(Object),
            mgrEmpNum: expect.any(Object),
            mealEmpNum: expect.any(Object),
            dscNum: expect.any(Object),
            dscMiNum: expect.any(Object),
            svcChgNum: expect.any(Object),
            tmedNum: expect.any(Object),
            miNum: expect.any(Object),
            guestCheckBI: expect.any(Object),
          }),
        );
      });
    });

    describe('getDetailLineBI', () => {
      it('should return NewDetailLineBI for default DetailLineBI initial value', () => {
        const formGroup = service.createDetailLineBIFormGroup(sampleWithNewData);

        const detailLineBI = service.getDetailLineBI(formGroup) as any;

        expect(detailLineBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewDetailLineBI for empty DetailLineBI initial value', () => {
        const formGroup = service.createDetailLineBIFormGroup();

        const detailLineBI = service.getDetailLineBI(formGroup) as any;

        expect(detailLineBI).toMatchObject({});
      });

      it('should return IDetailLineBI', () => {
        const formGroup = service.createDetailLineBIFormGroup(sampleWithRequiredData);

        const detailLineBI = service.getDetailLineBI(formGroup) as any;

        expect(detailLineBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDetailLineBI should not enable id FormControl', () => {
        const formGroup = service.createDetailLineBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDetailLineBI should disable id FormControl', () => {
        const formGroup = service.createDetailLineBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
