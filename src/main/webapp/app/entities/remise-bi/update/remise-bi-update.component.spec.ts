import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { RemiseBIService } from '../service/remise-bi.service';
import { IRemiseBI } from '../remise-bi.model';
import { RemiseBIFormService } from './remise-bi-form.service';

import { RemiseBIUpdateComponent } from './remise-bi-update.component';

describe('RemiseBI Management Update Component', () => {
  let comp: RemiseBIUpdateComponent;
  let fixture: ComponentFixture<RemiseBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let remiseBIFormService: RemiseBIFormService;
  let remiseBIService: RemiseBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RemiseBIUpdateComponent],
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
      .overrideTemplate(RemiseBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RemiseBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    remiseBIFormService = TestBed.inject(RemiseBIFormService);
    remiseBIService = TestBed.inject(RemiseBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const remiseBI: IRemiseBI = { id: 21786 };

      activatedRoute.data = of({ remiseBI });
      comp.ngOnInit();

      expect(comp.remiseBI).toEqual(remiseBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemiseBI>>();
      const remiseBI = { id: 31748 };
      jest.spyOn(remiseBIFormService, 'getRemiseBI').mockReturnValue(remiseBI);
      jest.spyOn(remiseBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remiseBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remiseBI }));
      saveSubject.complete();

      // THEN
      expect(remiseBIFormService.getRemiseBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(remiseBIService.update).toHaveBeenCalledWith(expect.objectContaining(remiseBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemiseBI>>();
      const remiseBI = { id: 31748 };
      jest.spyOn(remiseBIFormService, 'getRemiseBI').mockReturnValue({ id: null });
      jest.spyOn(remiseBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remiseBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remiseBI }));
      saveSubject.complete();

      // THEN
      expect(remiseBIFormService.getRemiseBI).toHaveBeenCalled();
      expect(remiseBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemiseBI>>();
      const remiseBI = { id: 31748 };
      jest.spyOn(remiseBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remiseBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(remiseBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
