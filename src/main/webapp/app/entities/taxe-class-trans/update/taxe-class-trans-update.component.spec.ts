import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TaxeClassTransService } from '../service/taxe-class-trans.service';
import { ITaxeClassTrans } from '../taxe-class-trans.model';
import { TaxeClassTransFormService } from './taxe-class-trans-form.service';

import { TaxeClassTransUpdateComponent } from './taxe-class-trans-update.component';

describe('TaxeClassTrans Management Update Component', () => {
  let comp: TaxeClassTransUpdateComponent;
  let fixture: ComponentFixture<TaxeClassTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taxeClassTransFormService: TaxeClassTransFormService;
  let taxeClassTransService: TaxeClassTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TaxeClassTransUpdateComponent],
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
      .overrideTemplate(TaxeClassTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaxeClassTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taxeClassTransFormService = TestBed.inject(TaxeClassTransFormService);
    taxeClassTransService = TestBed.inject(TaxeClassTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const taxeClassTrans: ITaxeClassTrans = { id: 30772 };

      activatedRoute.data = of({ taxeClassTrans });
      comp.ngOnInit();

      expect(comp.taxeClassTrans).toEqual(taxeClassTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeClassTrans>>();
      const taxeClassTrans = { id: 32691 };
      jest.spyOn(taxeClassTransFormService, 'getTaxeClassTrans').mockReturnValue(taxeClassTrans);
      jest.spyOn(taxeClassTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeClassTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxeClassTrans }));
      saveSubject.complete();

      // THEN
      expect(taxeClassTransFormService.getTaxeClassTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taxeClassTransService.update).toHaveBeenCalledWith(expect.objectContaining(taxeClassTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeClassTrans>>();
      const taxeClassTrans = { id: 32691 };
      jest.spyOn(taxeClassTransFormService, 'getTaxeClassTrans').mockReturnValue({ id: null });
      jest.spyOn(taxeClassTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeClassTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxeClassTrans }));
      saveSubject.complete();

      // THEN
      expect(taxeClassTransFormService.getTaxeClassTrans).toHaveBeenCalled();
      expect(taxeClassTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeClassTrans>>();
      const taxeClassTrans = { id: 32691 };
      jest.spyOn(taxeClassTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeClassTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taxeClassTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
