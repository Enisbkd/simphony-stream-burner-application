import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFamilyGroupCnC } from '../family-group-cn-c.model';
import { FamilyGroupCnCService } from '../service/family-group-cn-c.service';

import familyGroupCnCResolve from './family-group-cn-c-routing-resolve.service';

describe('FamilyGroupCnC routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FamilyGroupCnCService;
  let resultFamilyGroupCnC: IFamilyGroupCnC | null | undefined;

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
    service = TestBed.inject(FamilyGroupCnCService);
    resultFamilyGroupCnC = undefined;
  });

  describe('resolve', () => {
    it('should return IFamilyGroupCnC returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        familyGroupCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFamilyGroupCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFamilyGroupCnC).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        familyGroupCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFamilyGroupCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultFamilyGroupCnC).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFamilyGroupCnC>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        familyGroupCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFamilyGroupCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFamilyGroupCnC).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
