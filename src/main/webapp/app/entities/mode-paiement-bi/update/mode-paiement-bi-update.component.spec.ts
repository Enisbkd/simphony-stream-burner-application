import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ModePaiementBIService } from '../service/mode-paiement-bi.service';
import { IModePaiementBI } from '../mode-paiement-bi.model';
import { ModePaiementBIFormService } from './mode-paiement-bi-form.service';

import { ModePaiementBIUpdateComponent } from './mode-paiement-bi-update.component';

describe('ModePaiementBI Management Update Component', () => {
  let comp: ModePaiementBIUpdateComponent;
  let fixture: ComponentFixture<ModePaiementBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modePaiementBIFormService: ModePaiementBIFormService;
  let modePaiementBIService: ModePaiementBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ModePaiementBIUpdateComponent],
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
      .overrideTemplate(ModePaiementBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModePaiementBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modePaiementBIFormService = TestBed.inject(ModePaiementBIFormService);
    modePaiementBIService = TestBed.inject(ModePaiementBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const modePaiementBI: IModePaiementBI = { id: 32002 };

      activatedRoute.data = of({ modePaiementBI });
      comp.ngOnInit();

      expect(comp.modePaiementBI).toEqual(modePaiementBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiementBI>>();
      const modePaiementBI = { id: 25413 };
      jest.spyOn(modePaiementBIFormService, 'getModePaiementBI').mockReturnValue(modePaiementBI);
      jest.spyOn(modePaiementBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiementBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modePaiementBI }));
      saveSubject.complete();

      // THEN
      expect(modePaiementBIFormService.getModePaiementBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(modePaiementBIService.update).toHaveBeenCalledWith(expect.objectContaining(modePaiementBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiementBI>>();
      const modePaiementBI = { id: 25413 };
      jest.spyOn(modePaiementBIFormService, 'getModePaiementBI').mockReturnValue({ id: null });
      jest.spyOn(modePaiementBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiementBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modePaiementBI }));
      saveSubject.complete();

      // THEN
      expect(modePaiementBIFormService.getModePaiementBI).toHaveBeenCalled();
      expect(modePaiementBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiementBI>>();
      const modePaiementBI = { id: 25413 };
      jest.spyOn(modePaiementBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiementBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modePaiementBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
