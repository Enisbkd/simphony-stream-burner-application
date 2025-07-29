import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PointDeVenteCnCService } from '../service/point-de-vente-cn-c.service';
import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { PointDeVenteCnCFormService } from './point-de-vente-cn-c-form.service';

import { PointDeVenteCnCUpdateComponent } from './point-de-vente-cn-c-update.component';

describe('PointDeVenteCnC Management Update Component', () => {
  let comp: PointDeVenteCnCUpdateComponent;
  let fixture: ComponentFixture<PointDeVenteCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointDeVenteCnCFormService: PointDeVenteCnCFormService;
  let pointDeVenteCnCService: PointDeVenteCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PointDeVenteCnCUpdateComponent],
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
      .overrideTemplate(PointDeVenteCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointDeVenteCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointDeVenteCnCFormService = TestBed.inject(PointDeVenteCnCFormService);
    pointDeVenteCnCService = TestBed.inject(PointDeVenteCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const pointDeVenteCnC: IPointDeVenteCnC = { id: 10111 };

      activatedRoute.data = of({ pointDeVenteCnC });
      comp.ngOnInit();

      expect(comp.pointDeVenteCnC).toEqual(pointDeVenteCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVenteCnC>>();
      const pointDeVenteCnC = { id: 11208 };
      jest.spyOn(pointDeVenteCnCFormService, 'getPointDeVenteCnC').mockReturnValue(pointDeVenteCnC);
      jest.spyOn(pointDeVenteCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVenteCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointDeVenteCnC }));
      saveSubject.complete();

      // THEN
      expect(pointDeVenteCnCFormService.getPointDeVenteCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointDeVenteCnCService.update).toHaveBeenCalledWith(expect.objectContaining(pointDeVenteCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVenteCnC>>();
      const pointDeVenteCnC = { id: 11208 };
      jest.spyOn(pointDeVenteCnCFormService, 'getPointDeVenteCnC').mockReturnValue({ id: null });
      jest.spyOn(pointDeVenteCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVenteCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointDeVenteCnC }));
      saveSubject.complete();

      // THEN
      expect(pointDeVenteCnCFormService.getPointDeVenteCnC).toHaveBeenCalled();
      expect(pointDeVenteCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVenteCnC>>();
      const pointDeVenteCnC = { id: 11208 };
      jest.spyOn(pointDeVenteCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVenteCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointDeVenteCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
