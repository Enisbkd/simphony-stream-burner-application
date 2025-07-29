import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CheckTransService } from '../service/check-trans.service';
import { ICheckTrans } from '../check-trans.model';
import { CheckTransFormService } from './check-trans-form.service';

import { CheckTransUpdateComponent } from './check-trans-update.component';

describe('CheckTrans Management Update Component', () => {
  let comp: CheckTransUpdateComponent;
  let fixture: ComponentFixture<CheckTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let checkTransFormService: CheckTransFormService;
  let checkTransService: CheckTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CheckTransUpdateComponent],
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
      .overrideTemplate(CheckTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    checkTransFormService = TestBed.inject(CheckTransFormService);
    checkTransService = TestBed.inject(CheckTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const checkTrans: ICheckTrans = { id: 13051 };

      activatedRoute.data = of({ checkTrans });
      comp.ngOnInit();

      expect(comp.checkTrans).toEqual(checkTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckTrans>>();
      const checkTrans = { id: 2216 };
      jest.spyOn(checkTransFormService, 'getCheckTrans').mockReturnValue(checkTrans);
      jest.spyOn(checkTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkTrans }));
      saveSubject.complete();

      // THEN
      expect(checkTransFormService.getCheckTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(checkTransService.update).toHaveBeenCalledWith(expect.objectContaining(checkTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckTrans>>();
      const checkTrans = { id: 2216 };
      jest.spyOn(checkTransFormService, 'getCheckTrans').mockReturnValue({ id: null });
      jest.spyOn(checkTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkTrans }));
      saveSubject.complete();

      // THEN
      expect(checkTransFormService.getCheckTrans).toHaveBeenCalled();
      expect(checkTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckTrans>>();
      const checkTrans = { id: 2216 };
      jest.spyOn(checkTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(checkTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
