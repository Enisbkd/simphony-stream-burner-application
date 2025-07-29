import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';
import { MenuItemPricesCnCService } from '../service/menu-item-prices-cn-c.service';

import menuItemPricesCnCResolve from './menu-item-prices-cn-c-routing-resolve.service';

describe('MenuItemPricesCnC routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: MenuItemPricesCnCService;
  let resultMenuItemPricesCnC: IMenuItemPricesCnC | null | undefined;

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
    service = TestBed.inject(MenuItemPricesCnCService);
    resultMenuItemPricesCnC = undefined;
  });

  describe('resolve', () => {
    it('should return IMenuItemPricesCnC returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        menuItemPricesCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMenuItemPricesCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultMenuItemPricesCnC).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        menuItemPricesCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMenuItemPricesCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultMenuItemPricesCnC).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IMenuItemPricesCnC>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        menuItemPricesCnCResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultMenuItemPricesCnC = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultMenuItemPricesCnC).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
