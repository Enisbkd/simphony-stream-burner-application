import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../guest-check-bi.test-samples';

import { GuestCheckBIFormService } from './guest-check-bi-form.service';

describe('GuestCheckBI Form Service', () => {
  let service: GuestCheckBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuestCheckBIFormService);
  });

  describe('Service methods', () => {
    describe('createGuestCheckBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGuestCheckBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            guestCheckId: expect.any(Object),
            chkNum: expect.any(Object),
            opnLcl: expect.any(Object),
            clsdLcl: expect.any(Object),
            cancelFlag: expect.any(Object),
            gstCnt: expect.any(Object),
            tblNum: expect.any(Object),
            taxCollTtl: expect.any(Object),
            subTtl: expect.any(Object),
            chkTtl: expect.any(Object),
            svcChgTtl: expect.any(Object),
            tipTotal: expect.any(Object),
            dscTtl: expect.any(Object),
            errorCorrectTtl: expect.any(Object),
            returnTtl: expect.any(Object),
            xferToChkNum: expect.any(Object),
            xferStatus: expect.any(Object),
            otNum: expect.any(Object),
            locRef: expect.any(Object),
          }),
        );
      });

      it('passing IGuestCheckBI should create a new form with FormGroup', () => {
        const formGroup = service.createGuestCheckBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            guestCheckId: expect.any(Object),
            chkNum: expect.any(Object),
            opnLcl: expect.any(Object),
            clsdLcl: expect.any(Object),
            cancelFlag: expect.any(Object),
            gstCnt: expect.any(Object),
            tblNum: expect.any(Object),
            taxCollTtl: expect.any(Object),
            subTtl: expect.any(Object),
            chkTtl: expect.any(Object),
            svcChgTtl: expect.any(Object),
            tipTotal: expect.any(Object),
            dscTtl: expect.any(Object),
            errorCorrectTtl: expect.any(Object),
            returnTtl: expect.any(Object),
            xferToChkNum: expect.any(Object),
            xferStatus: expect.any(Object),
            otNum: expect.any(Object),
            locRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getGuestCheckBI', () => {
      it('should return NewGuestCheckBI for default GuestCheckBI initial value', () => {
        const formGroup = service.createGuestCheckBIFormGroup(sampleWithNewData);

        const guestCheckBI = service.getGuestCheckBI(formGroup) as any;

        expect(guestCheckBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewGuestCheckBI for empty GuestCheckBI initial value', () => {
        const formGroup = service.createGuestCheckBIFormGroup();

        const guestCheckBI = service.getGuestCheckBI(formGroup) as any;

        expect(guestCheckBI).toMatchObject({});
      });

      it('should return IGuestCheckBI', () => {
        const formGroup = service.createGuestCheckBIFormGroup(sampleWithRequiredData);

        const guestCheckBI = service.getGuestCheckBI(formGroup) as any;

        expect(guestCheckBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGuestCheckBI should not enable id FormControl', () => {
        const formGroup = service.createGuestCheckBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGuestCheckBI should disable id FormControl', () => {
        const formGroup = service.createGuestCheckBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
