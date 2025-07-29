import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IHttpCallAudit } from '../http-call-audit.model';
import { HttpCallAuditService } from '../service/http-call-audit.service';

import httpCallAuditResolve from './http-call-audit-routing-resolve.service';

describe('HttpCallAudit routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: HttpCallAuditService;
  let resultHttpCallAudit: IHttpCallAudit | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(HttpCallAuditService);
    resultHttpCallAudit = undefined;
  });

  describe('resolve', () => {
    it('should return IHttpCallAudit returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        httpCallAuditResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHttpCallAudit = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultHttpCallAudit).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        httpCallAuditResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHttpCallAudit = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultHttpCallAudit).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IHttpCallAudit>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        httpCallAuditResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHttpCallAudit = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultHttpCallAudit).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
