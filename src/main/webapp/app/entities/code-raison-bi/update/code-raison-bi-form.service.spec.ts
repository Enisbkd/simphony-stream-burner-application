import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../code-raison-bi.test-samples';

import { CodeRaisonBIFormService } from './code-raison-bi-form.service';

describe('CodeRaisonBI Form Service', () => {
  let service: CodeRaisonBIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeRaisonBIFormService);
  });

  describe('Service methods', () => {
    describe('createCodeRaisonBIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCodeRaisonBIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            numMstr: expect.any(Object),
            name: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing ICodeRaisonBI should create a new form with FormGroup', () => {
        const formGroup = service.createCodeRaisonBIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            numMstr: expect.any(Object),
            name: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getCodeRaisonBI', () => {
      it('should return NewCodeRaisonBI for default CodeRaisonBI initial value', () => {
        const formGroup = service.createCodeRaisonBIFormGroup(sampleWithNewData);

        const codeRaisonBI = service.getCodeRaisonBI(formGroup) as any;

        expect(codeRaisonBI).toMatchObject(sampleWithNewData);
      });

      it('should return NewCodeRaisonBI for empty CodeRaisonBI initial value', () => {
        const formGroup = service.createCodeRaisonBIFormGroup();

        const codeRaisonBI = service.getCodeRaisonBI(formGroup) as any;

        expect(codeRaisonBI).toMatchObject({});
      });

      it('should return ICodeRaisonBI', () => {
        const formGroup = service.createCodeRaisonBIFormGroup(sampleWithRequiredData);

        const codeRaisonBI = service.getCodeRaisonBI(formGroup) as any;

        expect(codeRaisonBI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICodeRaisonBI should not enable id FormControl', () => {
        const formGroup = service.createCodeRaisonBIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCodeRaisonBI should disable id FormControl', () => {
        const formGroup = service.createCodeRaisonBIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
