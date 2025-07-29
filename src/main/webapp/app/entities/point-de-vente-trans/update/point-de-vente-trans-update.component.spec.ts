import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PointDeVenteTransService } from '../service/point-de-vente-trans.service';
import { IPointDeVenteTrans } from '../point-de-vente-trans.model';
import { PointDeVenteTransFormService } from './point-de-vente-trans-form.service';

import { PointDeVenteTransUpdateComponent } from './point-de-vente-trans-update.component';

describe('PointDeVenteTrans Management Update Component', () => {
  let comp: PointDeVenteTransUpdateComponent;
  let fixture: ComponentFixture<PointDeVenteTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointDeVenteTransFormService: PointDeVenteTransFormService;
  let pointDeVenteTransService: PointDeVenteTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PointDeVenteTransUpdateComponent],
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
      .overrideTemplate(PointDeVenteTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointDeVenteTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointDeVenteTransFormService = TestBed.inject(PointDeVenteTransFormService);
    pointDeVenteTransService = TestBed.inject(PointDeVenteTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const pointDeVenteTrans: IPointDeVenteTrans = { id: 31903 };

      activatedRoute.data = of({ pointDeVenteTrans });
      comp.ngOnInit();

      expect(comp.pointDeVenteTrans).toEqual(pointDeVenteTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVenteTrans>>();
      const pointDeVenteTrans = { id: 3768 };
      jest.spyOn(pointDeVenteTransFormService, 'getPointDeVenteTrans').mockReturnValue(pointDeVenteTrans);
      jest.spyOn(pointDeVenteTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVenteTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointDeVenteTrans }));
      saveSubject.complete();

      // THEN
      expect(pointDeVenteTransFormService.getPointDeVenteTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointDeVenteTransService.update).toHaveBeenCalledWith(expect.objectContaining(pointDeVenteTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVenteTrans>>();
      const pointDeVenteTrans = { id: 3768 };
      jest.spyOn(pointDeVenteTransFormService, 'getPointDeVenteTrans').mockReturnValue({ id: null });
      jest.spyOn(pointDeVenteTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVenteTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointDeVenteTrans }));
      saveSubject.complete();

      // THEN
      expect(pointDeVenteTransFormService.getPointDeVenteTrans).toHaveBeenCalled();
      expect(pointDeVenteTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVenteTrans>>();
      const pointDeVenteTrans = { id: 3768 };
      jest.spyOn(pointDeVenteTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVenteTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointDeVenteTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
