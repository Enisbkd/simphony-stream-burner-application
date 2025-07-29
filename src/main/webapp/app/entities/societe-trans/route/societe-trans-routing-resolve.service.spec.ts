import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISocieteTrans } from '../societe-trans.model';
import { SocieteTransService } from '../service/societe-trans.service';

import societeTransResolve from './societe-trans-routing-resolve.service';

describe('SocieteTrans routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SocieteTransService;
  let resultSocieteTrans: ISocieteTrans | null | undefined;

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
    service = TestBed.inject(SocieteTransService);
    resultSocieteTrans = undefined;
  });

  describe('resolve', () => {
    it('should return ISocieteTrans returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        societeTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSocieteTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSocieteTrans).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        societeTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSocieteTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSocieteTrans).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISocieteTrans>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        societeTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSocieteTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSocieteTrans).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
