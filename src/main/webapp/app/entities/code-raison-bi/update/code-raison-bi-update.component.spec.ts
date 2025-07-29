import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CodeRaisonBIService } from '../service/code-raison-bi.service';
import { ICodeRaisonBI } from '../code-raison-bi.model';
import { CodeRaisonBIFormService } from './code-raison-bi-form.service';

import { CodeRaisonBIUpdateComponent } from './code-raison-bi-update.component';

describe('CodeRaisonBI Management Update Component', () => {
  let comp: CodeRaisonBIUpdateComponent;
  let fixture: ComponentFixture<CodeRaisonBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let codeRaisonBIFormService: CodeRaisonBIFormService;
  let codeRaisonBIService: CodeRaisonBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CodeRaisonBIUpdateComponent],
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
      .overrideTemplate(CodeRaisonBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CodeRaisonBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    codeRaisonBIFormService = TestBed.inject(CodeRaisonBIFormService);
    codeRaisonBIService = TestBed.inject(CodeRaisonBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const codeRaisonBI: ICodeRaisonBI = { id: 369 };

      activatedRoute.data = of({ codeRaisonBI });
      comp.ngOnInit();

      expect(comp.codeRaisonBI).toEqual(codeRaisonBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeRaisonBI>>();
      const codeRaisonBI = { id: 11250 };
      jest.spyOn(codeRaisonBIFormService, 'getCodeRaisonBI').mockReturnValue(codeRaisonBI);
      jest.spyOn(codeRaisonBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeRaisonBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeRaisonBI }));
      saveSubject.complete();

      // THEN
      expect(codeRaisonBIFormService.getCodeRaisonBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(codeRaisonBIService.update).toHaveBeenCalledWith(expect.objectContaining(codeRaisonBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeRaisonBI>>();
      const codeRaisonBI = { id: 11250 };
      jest.spyOn(codeRaisonBIFormService, 'getCodeRaisonBI').mockReturnValue({ id: null });
      jest.spyOn(codeRaisonBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeRaisonBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeRaisonBI }));
      saveSubject.complete();

      // THEN
      expect(codeRaisonBIFormService.getCodeRaisonBI).toHaveBeenCalled();
      expect(codeRaisonBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeRaisonBI>>();
      const codeRaisonBI = { id: 11250 };
      jest.spyOn(codeRaisonBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeRaisonBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(codeRaisonBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
