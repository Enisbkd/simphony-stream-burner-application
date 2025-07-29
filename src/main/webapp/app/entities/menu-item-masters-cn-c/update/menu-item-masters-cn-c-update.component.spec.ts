import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MenuItemMastersCnCService } from '../service/menu-item-masters-cn-c.service';
import { IMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';
import { MenuItemMastersCnCFormService } from './menu-item-masters-cn-c-form.service';

import { MenuItemMastersCnCUpdateComponent } from './menu-item-masters-cn-c-update.component';

describe('MenuItemMastersCnC Management Update Component', () => {
  let comp: MenuItemMastersCnCUpdateComponent;
  let fixture: ComponentFixture<MenuItemMastersCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let menuItemMastersCnCFormService: MenuItemMastersCnCFormService;
  let menuItemMastersCnCService: MenuItemMastersCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MenuItemMastersCnCUpdateComponent],
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
      .overrideTemplate(MenuItemMastersCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MenuItemMastersCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    menuItemMastersCnCFormService = TestBed.inject(MenuItemMastersCnCFormService);
    menuItemMastersCnCService = TestBed.inject(MenuItemMastersCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const menuItemMastersCnC: IMenuItemMastersCnC = { id: 30734 };

      activatedRoute.data = of({ menuItemMastersCnC });
      comp.ngOnInit();

      expect(comp.menuItemMastersCnC).toEqual(menuItemMastersCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemMastersCnC>>();
      const menuItemMastersCnC = { id: 27918 };
      jest.spyOn(menuItemMastersCnCFormService, 'getMenuItemMastersCnC').mockReturnValue(menuItemMastersCnC);
      jest.spyOn(menuItemMastersCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemMastersCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuItemMastersCnC }));
      saveSubject.complete();

      // THEN
      expect(menuItemMastersCnCFormService.getMenuItemMastersCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(menuItemMastersCnCService.update).toHaveBeenCalledWith(expect.objectContaining(menuItemMastersCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemMastersCnC>>();
      const menuItemMastersCnC = { id: 27918 };
      jest.spyOn(menuItemMastersCnCFormService, 'getMenuItemMastersCnC').mockReturnValue({ id: null });
      jest.spyOn(menuItemMastersCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemMastersCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuItemMastersCnC }));
      saveSubject.complete();

      // THEN
      expect(menuItemMastersCnCFormService.getMenuItemMastersCnC).toHaveBeenCalled();
      expect(menuItemMastersCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemMastersCnC>>();
      const menuItemMastersCnC = { id: 27918 };
      jest.spyOn(menuItemMastersCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemMastersCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(menuItemMastersCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
