import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IOrganizationLocationTrans } from '../organization-location-trans.model';
import { OrganizationLocationTransService } from '../service/organization-location-trans.service';

import organizationLocationTransResolve from './organization-location-trans-routing-resolve.service';

describe('OrganizationLocationTrans routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: OrganizationLocationTransService;
  let resultOrganizationLocationTrans: IOrganizationLocationTrans | null | undefined;

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
    service = TestBed.inject(OrganizationLocationTransService);
    resultOrganizationLocationTrans = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizationLocationTrans returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        organizationLocationTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOrganizationLocationTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultOrganizationLocationTrans).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        organizationLocationTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOrganizationLocationTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultOrganizationLocationTrans).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOrganizationLocationTrans>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        organizationLocationTransResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOrganizationLocationTrans = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultOrganizationLocationTrans).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
