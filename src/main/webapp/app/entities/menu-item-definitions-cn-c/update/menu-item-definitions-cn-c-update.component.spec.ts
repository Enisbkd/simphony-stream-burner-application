import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MenuItemDefinitionsCnCService } from '../service/menu-item-definitions-cn-c.service';
import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';
import { MenuItemDefinitionsCnCFormService } from './menu-item-definitions-cn-c-form.service';

import { MenuItemDefinitionsCnCUpdateComponent } from './menu-item-definitions-cn-c-update.component';

describe('MenuItemDefinitionsCnC Management Update Component', () => {
  let comp: MenuItemDefinitionsCnCUpdateComponent;
  let fixture: ComponentFixture<MenuItemDefinitionsCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let menuItemDefinitionsCnCFormService: MenuItemDefinitionsCnCFormService;
  let menuItemDefinitionsCnCService: MenuItemDefinitionsCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MenuItemDefinitionsCnCUpdateComponent],
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
      .overrideTemplate(MenuItemDefinitionsCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MenuItemDefinitionsCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    menuItemDefinitionsCnCFormService = TestBed.inject(MenuItemDefinitionsCnCFormService);
    menuItemDefinitionsCnCService = TestBed.inject(MenuItemDefinitionsCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const menuItemDefinitionsCnC: IMenuItemDefinitionsCnC = { id: 23772 };

      activatedRoute.data = of({ menuItemDefinitionsCnC });
      comp.ngOnInit();

      expect(comp.menuItemDefinitionsCnC).toEqual(menuItemDefinitionsCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemDefinitionsCnC>>();
      const menuItemDefinitionsCnC = { id: 19209 };
      jest.spyOn(menuItemDefinitionsCnCFormService, 'getMenuItemDefinitionsCnC').mockReturnValue(menuItemDefinitionsCnC);
      jest.spyOn(menuItemDefinitionsCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemDefinitionsCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuItemDefinitionsCnC }));
      saveSubject.complete();

      // THEN
      expect(menuItemDefinitionsCnCFormService.getMenuItemDefinitionsCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(menuItemDefinitionsCnCService.update).toHaveBeenCalledWith(expect.objectContaining(menuItemDefinitionsCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemDefinitionsCnC>>();
      const menuItemDefinitionsCnC = { id: 19209 };
      jest.spyOn(menuItemDefinitionsCnCFormService, 'getMenuItemDefinitionsCnC').mockReturnValue({ id: null });
      jest.spyOn(menuItemDefinitionsCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemDefinitionsCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: menuItemDefinitionsCnC }));
      saveSubject.complete();

      // THEN
      expect(menuItemDefinitionsCnCFormService.getMenuItemDefinitionsCnC).toHaveBeenCalled();
      expect(menuItemDefinitionsCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMenuItemDefinitionsCnC>>();
      const menuItemDefinitionsCnC = { id: 19209 };
      jest.spyOn(menuItemDefinitionsCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ menuItemDefinitionsCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(menuItemDefinitionsCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
