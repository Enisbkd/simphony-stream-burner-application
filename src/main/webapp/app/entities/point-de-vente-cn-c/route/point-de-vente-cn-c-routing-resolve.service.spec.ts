import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { PointDeVenteCnCService } from '../service/point-de-vente-cn-c.service';

import pointDeVenteCnCResolve from './point-de-vente-cn-c-routing-resolve.service';

describe('PointDeVenteCnC routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PointDeVenteCnCService;
  let resultPointDeVenteCnC: IPointDeVenteCnC | null | undefined;

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
    service = TestBed.inject(PointDeVenteCnCService);
    resultPointDeVenteCnC = undefined;
  });

  describe('resolve', () => {
    it('should return IPointDeVenteCnC returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        pointDeVenteCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPointDeVenteCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPointDeVenteCnC).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        pointDeVenteCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPointDeVenteCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultPointDeVenteCnC).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPointDeVenteCnC>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        pointDeVenteCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPointDeVenteCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPointDeVenteCnC).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
