import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICheckTrans } from '../check-trans.model';
import { CheckTransService } from '../service/check-trans.service';

import checkTransResolve from './check-trans-routing-resolve.service';

describe('CheckTrans routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CheckTransService;
  let resultCheckTrans: ICheckTrans | null | undefined;

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
    service = TestBed.inject(CheckTransService);
    resultCheckTrans = undefined;
  });

  describe('resolve', () => {
    it('should return ICheckTrans returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        checkTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCheckTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCheckTrans).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        checkTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCheckTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultCheckTrans).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICheckTrans>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        checkTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCheckTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCheckTrans).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
