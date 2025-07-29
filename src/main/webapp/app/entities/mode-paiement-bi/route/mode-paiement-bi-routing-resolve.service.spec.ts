import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IModePaiementBI } from '../mode-paiement-bi.model';
import { ModePaiementBIService } from '../service/mode-paiement-bi.service';

import modePaiementBIResolve from './mode-paiement-bi-routing-resolve.service';

describe('ModePaiementBI routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ModePaiementBIService;
  let resultModePaiementBI: IModePaiementBI | null | undefined;

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
    service = TestBed.inject(ModePaiementBIService);
    resultModePaiementBI = undefined;
  });

  describe('resolve', () => {
    it('should return IModePaiementBI returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        modePaiementBIResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultModePaiementBI = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultModePaiementBI).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        modePaiementBIResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultModePaiementBI = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultModePaiementBI).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IModePaiementBI>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        modePaiementBIResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultModePaiementBI = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultModePaiementBI).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
