import dayjs from 'dayjs/esm';

import { IHttpCallAudit, NewHttpCallAudit } from './http-call-audit.model';

export const sampleWithRequiredData: IHttpCallAudit = {
  id: 17019,
  correlationId: 'hydrant',
  method: 'stale inveigle',
  timestamp: dayjs('2025-07-28T14:29'),
  serviceName: 'cinema anti and',
  success: false,
  retryCount: 716,
};

export const sampleWithPartialData: IHttpCallAudit = {
  id: 11370,
  correlationId: 'plagiarise',
  method: 'before',
  requestHeaders: 'hundred drat configuration',
  requestBody: 'penalise midst across',
  responseHeaders: 'behold solder oxidize',
  responseBody: 'keenly petal',
  timestamp: dayjs('2025-07-28T13:52'),
  durationMs: 7406,
  serviceName: 'psst wherever wheel',
  success: false,
  retryCount: 28722,
};

export const sampleWithFullData: IHttpCallAudit = {
  id: 12236,
  correlationId: 'likely warmly',
  method: 'considering',
  basePath: 'depot drowse cautious',
  endpoint: 'probable extract suspiciously',
  fullUrl: 'than without frantically',
  pathParams: 'with',
  queryParams: 'following gut until',
  requestHeaders: 'mortally',
  requestBody: 'debut underneath',
  responseStatusCode: 16571,
  responseStatusText: 'mount fatally immediately',
  responseHeaders: 'sinful taro mild',
  responseBody: 'multicolored whimsical perky',
  timestamp: dayjs('2025-07-28T21:18'),
  durationMs: 30844,
  errorMessage: 'sturdy afore',
  errorType: 'mainstream sin readies',
  serviceName: 'muted overconfidently gah',
  environment: 'newsletter positively',
  userAgent: 'unlike',
  clientIp: 'shinny',
  success: true,
  retryCount: 20083,
  kafkaTopic: 'given gah',
  sessionId: 'platypus till velvety',
  userId: 'elegantly eyeliner',
};

export const sampleWithNewData: NewHttpCallAudit = {
  correlationId: 'closely youthfully slide',
  method: 'bog',
  timestamp: dayjs('2025-07-28T21:14'),
  serviceName: 'dirty rebound expert',
  success: true,
  retryCount: 7195,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
