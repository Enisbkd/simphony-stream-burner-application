import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../http-call-audit.test-samples';

import { HttpCallAuditFormService } from './http-call-audit-form.service';

describe('HttpCallAudit Form Service', () => {
  let service: HttpCallAuditFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpCallAuditFormService);
  });

  describe('Service methods', () => {
    describe('createHttpCallAuditFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHttpCallAuditFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            correlationId: expect.any(Object),
            method: expect.any(Object),
            basePath: expect.any(Object),
            endpoint: expect.any(Object),
            fullUrl: expect.any(Object),
            pathParams: expect.any(Object),
            queryParams: expect.any(Object),
            requestHeaders: expect.any(Object),
            requestBody: expect.any(Object),
            responseStatusCode: expect.any(Object),
            responseStatusText: expect.any(Object),
            responseHeaders: expect.any(Object),
            responseBody: expect.any(Object),
            timestamp: expect.any(Object),
            durationMs: expect.any(Object),
            errorMessage: expect.any(Object),
            errorType: expect.any(Object),
            serviceName: expect.any(Object),
            environment: expect.any(Object),
            userAgent: expect.any(Object),
            clientIp: expect.any(Object),
            success: expect.any(Object),
            retryCount: expect.any(Object),
            kafkaTopic: expect.any(Object),
            sessionId: expect.any(Object),
            userId: expect.any(Object),
          }),
        );
      });

      it('passing IHttpCallAudit should create a new form with FormGroup', () => {
        const formGroup = service.createHttpCallAuditFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            correlationId: expect.any(Object),
            method: expect.any(Object),
            basePath: expect.any(Object),
            endpoint: expect.any(Object),
            fullUrl: expect.any(Object),
            pathParams: expect.any(Object),
            queryParams: expect.any(Object),
            requestHeaders: expect.any(Object),
            requestBody: expect.any(Object),
            responseStatusCode: expect.any(Object),
            responseStatusText: expect.any(Object),
            responseHeaders: expect.any(Object),
            responseBody: expect.any(Object),
            timestamp: expect.any(Object),
            durationMs: expect.any(Object),
            errorMessage: expect.any(Object),
            errorType: expect.any(Object),
            serviceName: expect.any(Object),
            environment: expect.any(Object),
            userAgent: expect.any(Object),
            clientIp: expect.any(Object),
            success: expect.any(Object),
            retryCount: expect.any(Object),
            kafkaTopic: expect.any(Object),
            sessionId: expect.any(Object),
            userId: expect.any(Object),
          }),
        );
      });
    });

    describe('getHttpCallAudit', () => {
      it('should return NewHttpCallAudit for default HttpCallAudit initial value', () => {
        const formGroup = service.createHttpCallAuditFormGroup(sampleWithNewData);

        const httpCallAudit = service.getHttpCallAudit(formGroup) as any;

        expect(httpCallAudit).toMatchObject(sampleWithNewData);
      });

      it('should return NewHttpCallAudit for empty HttpCallAudit initial value', () => {
        const formGroup = service.createHttpCallAuditFormGroup();

        const httpCallAudit = service.getHttpCallAudit(formGroup) as any;

        expect(httpCallAudit).toMatchObject({});
      });

      it('should return IHttpCallAudit', () => {
        const formGroup = service.createHttpCallAuditFormGroup(sampleWithRequiredData);

        const httpCallAudit = service.getHttpCallAudit(formGroup) as any;

        expect(httpCallAudit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHttpCallAudit should not enable id FormControl', () => {
        const formGroup = service.createHttpCallAuditFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHttpCallAudit should disable id FormControl', () => {
        const formGroup = service.createHttpCallAuditFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
