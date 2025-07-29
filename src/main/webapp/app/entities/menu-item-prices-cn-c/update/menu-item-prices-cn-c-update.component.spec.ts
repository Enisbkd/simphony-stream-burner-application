import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MenuItemPricesCnCService } from '../service/menu-item-prices-cn-c.service';
import { IMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';
import { MenuItemPricesCnCFormService } from './menu-item-prices-cn-c-form.service';

import { MenuItemPricesCnCUpdateComponent } from './menu-item-prices-cn-c-update.component';

describe('MenuItemPricesCnC Management Update Component', () => {
  let comp: MenuItemPricesCnCUpdateComponent;
  let fixture: ComponentFixture<MenuItemPricesCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let menuItemPricesCnCFormService: MenuItemPricesCnCFormService;
  let menuItemPricesCnCService: MenuItemPricesCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MenuItemPricesCnCUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MenuItemPricesCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MenuItemPricesCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    menuItemPricesCnCFormService = TestBed.inject(MenuItemPricesCnCFormService);
    menuItemPricesCnCService = TestBed.inject(MenuItemPricesCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const menuItemPricesCnC: IMenuItemPricesCnC = { id: 29361 };

      activatedRoute.data = of({ menuItemPricesCnC });
      comp.ngOnInit();

      expect(comp.menuItemPricesCnC).toEqual(menuItemPricesCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemPricesCnC>>();
      const menuItemPricesCnC = { id: 11591 };
      jest.spyOn(menuItemPricesCnCFormService, 'getMenuItemPricesCnC').mockReturnValue(menuItemPricesCnC);
      jest.spyOn(menuItemPricesCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemPricesCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuItemPricesCnC }));
      saveSubject.complete();

      // THEN
      expect(menuItemPricesCnCFormService.getMenuItemPricesCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(menuItemPricesCnCService.update).toHaveBeenCalledWith(expect.objectContaining(menuItemPricesCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemPricesCnC>>();
      const menuItemPricesCnC = { id: 11591 };
      jest.spyOn(menuItemPricesCnCFormService, 'getMenuItemPricesCnC').mockReturnValue({ id: null });
      jest.spyOn(menuItemPricesCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemPricesCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuItemPricesCnC }));
      saveSubject.complete();

      // THEN
      expect(menuItemPricesCnCFormService.getMenuItemPricesCnC).toHaveBeenCalled();
      expect(menuItemPricesCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemPricesCnC>>();
      const menuItemPricesCnC = { id: 11591 };
      jest.spyOn(menuItemPricesCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemPricesCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(menuItemPricesCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
