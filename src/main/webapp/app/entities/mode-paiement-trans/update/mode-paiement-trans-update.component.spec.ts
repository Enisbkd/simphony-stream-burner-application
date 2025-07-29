import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ModePaiementTransService } from '../service/mode-paiement-trans.service';
import { IModePaiementTrans } from '../mode-paiement-trans.model';
import { ModePaiementTransFormService } from './mode-paiement-trans-form.service';

import { ModePaiementTransUpdateComponent } from './mode-paiement-trans-update.component';

describe('ModePaiementTrans Management Update Component', () => {
  let comp: ModePaiementTransUpdateComponent;
  let fixture: ComponentFixture<ModePaiementTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modePaiementTransFormService: ModePaiementTransFormService;
  let modePaiementTransService: ModePaiementTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ModePaiementTransUpdateComponent],
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
      .overrideTemplate(ModePaiementTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModePaiementTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modePaiementTransFormService = TestBed.inject(ModePaiementTransFormService);
    modePaiementTransService = TestBed.inject(ModePaiementTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const modePaiementTrans: IModePaiementTrans = { id: 26549 };

      activatedRoute.data = of({ modePaiementTrans });
      comp.ngOnInit();

      expect(comp.modePaiementTrans).toEqual(modePaiementTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiementTrans>>();
      const modePaiementTrans = { id: 15721 };
      jest.spyOn(modePaiementTransFormService, 'getModePaiementTrans').mockReturnValue(modePaiementTrans);
      jest.spyOn(modePaiementTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiementTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modePaiementTrans }));
      saveSubject.complete();

      // THEN
      expect(modePaiementTransFormService.getModePaiementTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(modePaiementTransService.update).toHaveBeenCalledWith(expect.objectContaining(modePaiementTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiementTrans>>();
      const modePaiementTrans = { id: 15721 };
      jest.spyOn(modePaiementTransFormService, 'getModePaiementTrans').mockReturnValue({ id: null });
      jest.spyOn(modePaiementTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiementTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modePaiementTrans }));
      saveSubject.complete();

      // THEN
      expect(modePaiementTransFormService.getModePaiementTrans).toHaveBeenCalled();
      expect(modePaiementTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiementTrans>>();
      const modePaiementTrans = { id: 15721 };
      jest.spyOn(modePaiementTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiementTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modePaiementTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
