import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SocieteTransService } from '../service/societe-trans.service';
import { ISocieteTrans } from '../societe-trans.model';
import { SocieteTransFormService } from './societe-trans-form.service';

import { SocieteTransUpdateComponent } from './societe-trans-update.component';

describe('SocieteTrans Management Update Component', () => {
  let comp: SocieteTransUpdateComponent;
  let fixture: ComponentFixture<SocieteTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let societeTransFormService: SocieteTransFormService;
  let societeTransService: SocieteTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SocieteTransUpdateComponent],
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
      .overrideTemplate(SocieteTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocieteTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    societeTransFormService = TestBed.inject(SocieteTransFormService);
    societeTransService = TestBed.inject(SocieteTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const societeTrans: ISocieteTrans = { id: 10458 };

      activatedRoute.data = of({ societeTrans });
      comp.ngOnInit();

      expect(comp.societeTrans).toEqual(societeTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocieteTrans>>();
      const societeTrans = { id: 1464 };
      jest.spyOn(societeTransFormService, 'getSocieteTrans').mockReturnValue(societeTrans);
      jest.spyOn(societeTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societeTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societeTrans }));
      saveSubject.complete();

      // THEN
      expect(societeTransFormService.getSocieteTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(societeTransService.update).toHaveBeenCalledWith(expect.objectContaining(societeTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocieteTrans>>();
      const societeTrans = { id: 1464 };
      jest.spyOn(societeTransFormService, 'getSocieteTrans').mockReturnValue({ id: null });
      jest.spyOn(societeTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societeTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: societeTrans }));
      saveSubject.complete();

      // THEN
      expect(societeTransFormService.getSocieteTrans).toHaveBeenCalled();
      expect(societeTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISocieteTrans>>();
      const societeTrans = { id: 1464 };
      jest.spyOn(societeTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ societeTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(societeTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
