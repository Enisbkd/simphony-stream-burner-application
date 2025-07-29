import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FamilyGroupCnCService } from '../service/family-group-cn-c.service';
import { IFamilyGroupCnC } from '../family-group-cn-c.model';
import { FamilyGroupCnCFormService } from './family-group-cn-c-form.service';

import { FamilyGroupCnCUpdateComponent } from './family-group-cn-c-update.component';

describe('FamilyGroupCnC Management Update Component', () => {
  let comp: FamilyGroupCnCUpdateComponent;
  let fixture: ComponentFixture<FamilyGroupCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let familyGroupCnCFormService: FamilyGroupCnCFormService;
  let familyGroupCnCService: FamilyGroupCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FamilyGroupCnCUpdateComponent],
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
      .overrideTemplate(FamilyGroupCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FamilyGroupCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    familyGroupCnCFormService = TestBed.inject(FamilyGroupCnCFormService);
    familyGroupCnCService = TestBed.inject(FamilyGroupCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const familyGroupCnC: IFamilyGroupCnC = { id: 8835 };

      activatedRoute.data = of({ familyGroupCnC });
      comp.ngOnInit();

      expect(comp.familyGroupCnC).toEqual(familyGroupCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyGroupCnC>>();
      const familyGroupCnC = { id: 16235 };
      jest.spyOn(familyGroupCnCFormService, 'getFamilyGroupCnC').mockReturnValue(familyGroupCnC);
      jest.spyOn(familyGroupCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyGroupCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: familyGroupCnC }));
      saveSubject.complete();

      // THEN
      expect(familyGroupCnCFormService.getFamilyGroupCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(familyGroupCnCService.update).toHaveBeenCalledWith(expect.objectContaining(familyGroupCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyGroupCnC>>();
      const familyGroupCnC = { id: 16235 };
      jest.spyOn(familyGroupCnCFormService, 'getFamilyGroupCnC').mockReturnValue({ id: null });
      jest.spyOn(familyGroupCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyGroupCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: familyGroupCnC }));
      saveSubject.complete();

      // THEN
      expect(familyGroupCnCFormService.getFamilyGroupCnC).toHaveBeenCalled();
      expect(familyGroupCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyGroupCnC>>();
      const familyGroupCnC = { id: 16235 };
      jest.spyOn(familyGroupCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyGroupCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(familyGroupCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
