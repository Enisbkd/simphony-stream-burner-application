import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TaxeRateTransService } from '../service/taxe-rate-trans.service';
import { ITaxeRateTrans } from '../taxe-rate-trans.model';
import { TaxeRateTransFormService } from './taxe-rate-trans-form.service';

import { TaxeRateTransUpdateComponent } from './taxe-rate-trans-update.component';

describe('TaxeRateTrans Management Update Component', () => {
  let comp: TaxeRateTransUpdateComponent;
  let fixture: ComponentFixture<TaxeRateTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taxeRateTransFormService: TaxeRateTransFormService;
  let taxeRateTransService: TaxeRateTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TaxeRateTransUpdateComponent],
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
      .overrideTemplate(TaxeRateTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaxeRateTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taxeRateTransFormService = TestBed.inject(TaxeRateTransFormService);
    taxeRateTransService = TestBed.inject(TaxeRateTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const taxeRateTrans: ITaxeRateTrans = { id: 8059 };

      activatedRoute.data = of({ taxeRateTrans });
      comp.ngOnInit();

      expect(comp.taxeRateTrans).toEqual(taxeRateTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeRateTrans>>();
      const taxeRateTrans = { id: 7322 };
      jest.spyOn(taxeRateTransFormService, 'getTaxeRateTrans').mockReturnValue(taxeRateTrans);
      jest.spyOn(taxeRateTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeRateTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxeRateTrans }));
      saveSubject.complete();

      // THEN
      expect(taxeRateTransFormService.getTaxeRateTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taxeRateTransService.update).toHaveBeenCalledWith(expect.objectContaining(taxeRateTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeRateTrans>>();
      const taxeRateTrans = { id: 7322 };
      jest.spyOn(taxeRateTransFormService, 'getTaxeRateTrans').mockReturnValue({ id: null });
      jest.spyOn(taxeRateTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeRateTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxeRateTrans }));
      saveSubject.complete();

      // THEN
      expect(taxeRateTransFormService.getTaxeRateTrans).toHaveBeenCalled();
      expect(taxeRateTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeRateTrans>>();
      const taxeRateTrans = { id: 7322 };
      jest.spyOn(taxeRateTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeRateTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taxeRateTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
