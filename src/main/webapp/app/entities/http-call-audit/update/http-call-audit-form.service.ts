import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHttpCallAudit, NewHttpCallAudit } from '../http-call-audit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHttpCallAudit for edit and NewHttpCallAuditFormGroupInput for create.
 */
type HttpCallAuditFormGroupInput = IHttpCallAudit | PartialWithRequiredKeyOf<NewHttpCallAudit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHttpCallAudit | NewHttpCallAudit> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type HttpCallAuditFormRawValue = FormValueOf<IHttpCallAudit>;

type NewHttpCallAuditFormRawValue = FormValueOf<NewHttpCallAudit>;

type HttpCallAuditFormDefaults = Pick<NewHttpCallAudit, 'id' | 'timestamp' | 'success'>;

type HttpCallAuditFormGroupContent = {
  id: FormControl<HttpCallAuditFormRawValue['id'] | NewHttpCallAudit['id']>;
  correlationId: FormControl<HttpCallAuditFormRawValue['correlationId']>;
  method: FormControl<HttpCallAuditFormRawValue['method']>;
  basePath: FormControl<HttpCallAuditFormRawValue['basePath']>;
  endpoint: FormControl<HttpCallAuditFormRawValue['endpoint']>;
  fullUrl: FormControl<HttpCallAuditFormRawValue['fullUrl']>;
  pathParams: FormControl<HttpCallAuditFormRawValue['pathParams']>;
  queryParams: FormControl<HttpCallAuditFormRawValue['queryParams']>;
  requestHeaders: FormControl<HttpCallAuditFormRawValue['requestHeaders']>;
  requestBody: FormControl<HttpCallAuditFormRawValue['requestBody']>;
  responseStatusCode: FormControl<HttpCallAuditFormRawValue['responseStatusCode']>;
  responseStatusText: FormControl<HttpCallAuditFormRawValue['responseStatusText']>;
  responseHeaders: FormControl<HttpCallAuditFormRawValue['responseHeaders']>;
  responseBody: FormControl<HttpCallAuditFormRawValue['responseBody']>;
  timestamp: FormControl<HttpCallAuditFormRawValue['timestamp']>;
  durationMs: FormControl<HttpCallAuditFormRawValue['durationMs']>;
  errorMessage: FormControl<HttpCallAuditFormRawValue['errorMessage']>;
  errorType: FormControl<HttpCallAuditFormRawValue['errorType']>;
  serviceName: FormControl<HttpCallAuditFormRawValue['serviceName']>;
  environment: FormControl<HttpCallAuditFormRawValue['environment']>;
  userAgent: FormControl<HttpCallAuditFormRawValue['userAgent']>;
  clientIp: FormControl<HttpCallAuditFormRawValue['clientIp']>;
  success: FormControl<HttpCallAuditFormRawValue['success']>;
  retryCount: FormControl<HttpCallAuditFormRawValue['retryCount']>;
  kafkaTopic: FormControl<HttpCallAuditFormRawValue['kafkaTopic']>;
  sessionId: FormControl<HttpCallAuditFormRawValue['sessionId']>;
  userId: FormControl<HttpCallAuditFormRawValue['userId']>;
};

export type HttpCallAuditFormGroup = FormGroup<HttpCallAuditFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HttpCallAuditFormService {
  createHttpCallAuditFormGroup(httpCallAudit: HttpCallAuditFormGroupInput = { id: null }): HttpCallAuditFormGroup {
    const httpCallAuditRawValue = this.convertHttpCallAuditToHttpCallAuditRawValue({
      ...this.getFormDefaults(),
      ...httpCallAudit,
    });
    return new FormGroup<HttpCallAuditFormGroupContent>({
      id: new FormControl(
        { value: httpCallAuditRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      correlationId: new FormControl(httpCallAuditRawValue.correlationId, {
        validators: [Validators.required],
      }),
      method: new FormControl(httpCallAuditRawValue.method, {
        validators: [Validators.required],
      }),
      basePath: new FormControl(httpCallAuditRawValue.basePath),
      endpoint: new FormControl(httpCallAuditRawValue.endpoint),
      fullUrl: new FormControl(httpCallAuditRawValue.fullUrl),
      pathParams: new FormControl(httpCallAuditRawValue.pathParams),
      queryParams: new FormControl(httpCallAuditRawValue.queryParams),
      requestHeaders: new FormControl(httpCallAuditRawValue.requestHeaders),
      requestBody: new FormControl(httpCallAuditRawValue.requestBody),
      responseStatusCode: new FormControl(httpCallAuditRawValue.responseStatusCode),
      responseStatusText: new FormControl(httpCallAuditRawValue.responseStatusText),
      responseHeaders: new FormControl(httpCallAuditRawValue.responseHeaders),
      responseBody: new FormControl(httpCallAuditRawValue.responseBody),
      timestamp: new FormControl(httpCallAuditRawValue.timestamp, {
        validators: [Validators.required],
      }),
      durationMs: new FormControl(httpCallAuditRawValue.durationMs),
      errorMessage: new FormControl(httpCallAuditRawValue.errorMessage),
      errorType: new FormControl(httpCallAuditRawValue.errorType),
      serviceName: new FormControl(httpCallAuditRawValue.serviceName, {
        validators: [Validators.required],
      }),
      environment: new FormControl(httpCallAuditRawValue.environment),
      userAgent: new FormControl(httpCallAuditRawValue.userAgent),
      clientIp: new FormControl(httpCallAuditRawValue.clientIp),
      success: new FormControl(httpCallAuditRawValue.success, {
        validators: [Validators.required],
      }),
      retryCount: new FormControl(httpCallAuditRawValue.retryCount, {
        validators: [Validators.required],
      }),
      kafkaTopic: new FormControl(httpCallAuditRawValue.kafkaTopic),
      sessionId: new FormControl(httpCallAuditRawValue.sessionId),
      userId: new FormControl(httpCallAuditRawValue.userId),
    });
  }

  getHttpCallAudit(form: HttpCallAuditFormGroup): IHttpCallAudit | NewHttpCallAudit {
    return this.convertHttpCallAuditRawValueToHttpCallAudit(form.getRawValue() as HttpCallAuditFormRawValue | NewHttpCallAuditFormRawValue);
  }

  resetForm(form: HttpCallAuditFormGroup, httpCallAudit: HttpCallAuditFormGroupInput): void {
    const httpCallAuditRawValue = this.convertHttpCallAuditToHttpCallAuditRawValue({ ...this.getFormDefaults(), ...httpCallAudit });
    form.reset(
      {
        ...httpCallAuditRawValue,
        id: { value: httpCallAuditRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HttpCallAuditFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
      success: false,
    };
  }

  private convertHttpCallAuditRawValueToHttpCallAudit(
    rawHttpCallAudit: HttpCallAuditFormRawValue | NewHttpCallAuditFormRawValue,
  ): IHttpCallAudit | NewHttpCallAudit {
    return {
      ...rawHttpCallAudit,
      timestamp: dayjs(rawHttpCallAudit.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertHttpCallAuditToHttpCallAuditRawValue(
    httpCallAudit: IHttpCallAudit | (Partial<NewHttpCallAudit> & HttpCallAuditFormDefaults),
  ): HttpCallAuditFormRawValue | PartialWithRequiredKeyOf<NewHttpCallAuditFormRawValue> {
    return {
      ...httpCallAudit,
      timestamp: httpCallAudit.timestamp ? httpCallAudit.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
