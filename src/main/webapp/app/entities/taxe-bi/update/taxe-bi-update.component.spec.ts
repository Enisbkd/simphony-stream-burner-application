import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TaxeBIService } from '../service/taxe-bi.service';
import { ITaxeBI } from '../taxe-bi.model';
import { TaxeBIFormService } from './taxe-bi-form.service';

import { TaxeBIUpdateComponent } from './taxe-bi-update.component';

describe('TaxeBI Management Update Component', () => {
  let comp: TaxeBIUpdateComponent;
  let fixture: ComponentFixture<TaxeBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taxeBIFormService: TaxeBIFormService;
  let taxeBIService: TaxeBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TaxeBIUpdateComponent],
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
      .overrideTemplate(TaxeBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaxeBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taxeBIFormService = TestBed.inject(TaxeBIFormService);
    taxeBIService = TestBed.inject(TaxeBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const taxeBI: ITaxeBI = { id: 3761 };

      activatedRoute.data = of({ taxeBI });
      comp.ngOnInit();

      expect(comp.taxeBI).toEqual(taxeBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeBI>>();
      const taxeBI = { id: 18086 };
      jest.spyOn(taxeBIFormService, 'getTaxeBI').mockReturnValue(taxeBI);
      jest.spyOn(taxeBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxeBI }));
      saveSubject.complete();

      // THEN
      expect(taxeBIFormService.getTaxeBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taxeBIService.update).toHaveBeenCalledWith(expect.objectContaining(taxeBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeBI>>();
      const taxeBI = { id: 18086 };
      jest.spyOn(taxeBIFormService, 'getTaxeBI').mockReturnValue({ id: null });
      jest.spyOn(taxeBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxeBI }));
      saveSubject.complete();

      // THEN
      expect(taxeBIFormService.getTaxeBI).toHaveBeenCalled();
      expect(taxeBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxeBI>>();
      const taxeBI = { id: 18086 };
      jest.spyOn(taxeBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxeBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taxeBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
