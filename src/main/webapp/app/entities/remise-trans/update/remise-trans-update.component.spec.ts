import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { RemiseTransService } from '../service/remise-trans.service';
import { IRemiseTrans } from '../remise-trans.model';
import { RemiseTransFormService } from './remise-trans-form.service';

import { RemiseTransUpdateComponent } from './remise-trans-update.component';

describe('RemiseTrans Management Update Component', () => {
  let comp: RemiseTransUpdateComponent;
  let fixture: ComponentFixture<RemiseTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let remiseTransFormService: RemiseTransFormService;
  let remiseTransService: RemiseTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RemiseTransUpdateComponent],
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
      .overrideTemplate(RemiseTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RemiseTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    remiseTransFormService = TestBed.inject(RemiseTransFormService);
    remiseTransService = TestBed.inject(RemiseTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const remiseTrans: IRemiseTrans = { id: 28204 };

      activatedRoute.data = of({ remiseTrans });
      comp.ngOnInit();

      expect(comp.remiseTrans).toEqual(remiseTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemiseTrans>>();
      const remiseTrans = { id: 19837 };
      jest.spyOn(remiseTransFormService, 'getRemiseTrans').mockReturnValue(remiseTrans);
      jest.spyOn(remiseTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remiseTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remiseTrans }));
      saveSubject.complete();

      // THEN
      expect(remiseTransFormService.getRemiseTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(remiseTransService.update).toHaveBeenCalledWith(expect.objectContaining(remiseTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemiseTrans>>();
      const remiseTrans = { id: 19837 };
      jest.spyOn(remiseTransFormService, 'getRemiseTrans').mockReturnValue({ id: null });
      jest.spyOn(remiseTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remiseTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remiseTrans }));
      saveSubject.complete();

      // THEN
      expect(remiseTransFormService.getRemiseTrans).toHaveBeenCalled();
      expect(remiseTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemiseTrans>>();
      const remiseTrans = { id: 19837 };
      jest.spyOn(remiseTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remiseTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(remiseTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
