import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IMajorGroupCnC } from '../major-group-cn-c.model';
import { MajorGroupCnCService } from '../service/major-group-cn-c.service';

import majorGroupCnCResolve from './major-group-cn-c-routing-resolve.service';

describe('MajorGroupCnC routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: MajorGroupCnCService;
  let resultMajorGroupCnC: IMajorGroupCnC | null | undefined;

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
    service = TestBed.inject(MajorGroupCnCService);
    resultMajorGroupCnC = undefined;
  });

  describe('resolve', () => {
    it('should return IMajorGroupCnC returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        majorGroupCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMajorGroupCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultMajorGroupCnC).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        majorGroupCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMajorGroupCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultMajorGroupCnC).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IMajorGroupCnC>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        majorGroupCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMajorGroupCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultMajorGroupCnC).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
