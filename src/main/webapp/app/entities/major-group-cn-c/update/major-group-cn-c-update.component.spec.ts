import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MajorGroupCnCService } from '../service/major-group-cn-c.service';
import { IMajorGroupCnC } from '../major-group-cn-c.model';
import { MajorGroupCnCFormService } from './major-group-cn-c-form.service';

import { MajorGroupCnCUpdateComponent } from './major-group-cn-c-update.component';

describe('MajorGroupCnC Management Update Component', () => {
  let comp: MajorGroupCnCUpdateComponent;
  let fixture: ComponentFixture<MajorGroupCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let majorGroupCnCFormService: MajorGroupCnCFormService;
  let majorGroupCnCService: MajorGroupCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MajorGroupCnCUpdateComponent],
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
      .overrideTemplate(MajorGroupCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MajorGroupCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    majorGroupCnCFormService = TestBed.inject(MajorGroupCnCFormService);
    majorGroupCnCService = TestBed.inject(MajorGroupCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const majorGroupCnC: IMajorGroupCnC = { id: 27026 };

      activatedRoute.data = of({ majorGroupCnC });
      comp.ngOnInit();

      expect(comp.majorGroupCnC).toEqual(majorGroupCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajorGroupCnC>>();
      const majorGroupCnC = { id: 17824 };
      jest.spyOn(majorGroupCnCFormService, 'getMajorGroupCnC').mockReturnValue(majorGroupCnC);
      jest.spyOn(majorGroupCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ majorGroupCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: majorGroupCnC }));
      saveSubject.complete();

      // THEN
      expect(majorGroupCnCFormService.getMajorGroupCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(majorGroupCnCService.update).toHaveBeenCalledWith(expect.objectContaining(majorGroupCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajorGroupCnC>>();
      const majorGroupCnC = { id: 17824 };
      jest.spyOn(majorGroupCnCFormService, 'getMajorGroupCnC').mockReturnValue({ id: null });
      jest.spyOn(majorGroupCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ majorGroupCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: majorGroupCnC }));
      saveSubject.complete();

      // THEN
      expect(majorGroupCnCFormService.getMajorGroupCnC).toHaveBeenCalled();
      expect(majorGroupCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajorGroupCnC>>();
      const majorGroupCnC = { id: 17824 };
      jest.spyOn(majorGroupCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ majorGroupCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(majorGroupCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
