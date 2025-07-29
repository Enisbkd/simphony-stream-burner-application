import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { BarCodeTransService } from '../service/bar-code-trans.service';
import { IBarCodeTrans } from '../bar-code-trans.model';
import { BarCodeTransFormService } from './bar-code-trans-form.service';

import { BarCodeTransUpdateComponent } from './bar-code-trans-update.component';

describe('BarCodeTrans Management Update Component', () => {
  let comp: BarCodeTransUpdateComponent;
  let fixture: ComponentFixture<BarCodeTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let barCodeTransFormService: BarCodeTransFormService;
  let barCodeTransService: BarCodeTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [BarCodeTransUpdateComponent],
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
      .overrideTemplate(BarCodeTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BarCodeTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    barCodeTransFormService = TestBed.inject(BarCodeTransFormService);
    barCodeTransService = TestBed.inject(BarCodeTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const barCodeTrans: IBarCodeTrans = { id: 9235 };

      activatedRoute.data = of({ barCodeTrans });
      comp.ngOnInit();

      expect(comp.barCodeTrans).toEqual(barCodeTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBarCodeTrans>>();
      const barCodeTrans = { id: 19267 };
      jest.spyOn(barCodeTransFormService, 'getBarCodeTrans').mockReturnValue(barCodeTrans);
      jest.spyOn(barCodeTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ barCodeTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: barCodeTrans }));
      saveSubject.complete();

      // THEN
      expect(barCodeTransFormService.getBarCodeTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(barCodeTransService.update).toHaveBeenCalledWith(expect.objectContaining(barCodeTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBarCodeTrans>>();
      const barCodeTrans = { id: 19267 };
      jest.spyOn(barCodeTransFormService, 'getBarCodeTrans').mockReturnValue({ id: null });
      jest.spyOn(barCodeTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ barCodeTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: barCodeTrans }));
      saveSubject.complete();

      // THEN
      expect(barCodeTransFormService.getBarCodeTrans).toHaveBeenCalled();
      expect(barCodeTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBarCodeTrans>>();
      const barCodeTrans = { id: 19267 };
      jest.spyOn(barCodeTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ barCodeTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(barCodeTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
