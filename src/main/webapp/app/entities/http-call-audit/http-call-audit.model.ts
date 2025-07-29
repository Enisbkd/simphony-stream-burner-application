import dayjs from 'dayjs/esm';

export interface IHttpCallAudit {
  id: number;
  correlationId?: string | null;
  method?: string | null;
  basePath?: string | null;
  endpoint?: string | null;
  fullUrl?: string | null;
  pathParams?: string | null;
  queryParams?: string | null;
  requestHeaders?: string | null;
  requestBody?: string | null;
  responseStatusCode?: number | null;
  responseStatusText?: string | null;
  responseHeaders?: string | null;
  responseBody?: string | null;
  timestamp?: dayjs.Dayjs | null;
  durationMs?: number | null;
  errorMessage?: string | null;
  errorType?: string | null;
  serviceName?: string | null;
  environment?: string | null;
  userAgent?: string | null;
  clientIp?: string | null;
  success?: boolean | null;
  retryCount?: number | null;
  kafkaTopic?: string | null;
  sessionId?: string | null;
  userId?: string | null;
}

export type NewHttpCallAudit = Omit<IHttpCallAudit, 'id'> & { id: null };
