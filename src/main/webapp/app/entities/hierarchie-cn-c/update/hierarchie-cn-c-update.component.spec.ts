import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { HierarchieCnCService } from '../service/hierarchie-cn-c.service';
import { IHierarchieCnC } from '../hierarchie-cn-c.model';
import { HierarchieCnCFormService } from './hierarchie-cn-c-form.service';

import { HierarchieCnCUpdateComponent } from './hierarchie-cn-c-update.component';

describe('HierarchieCnC Management Update Component', () => {
  let comp: HierarchieCnCUpdateComponent;
  let fixture: ComponentFixture<HierarchieCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hierarchieCnCFormService: HierarchieCnCFormService;
  let hierarchieCnCService: HierarchieCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HierarchieCnCUpdateComponent],
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
      .overrideTemplate(HierarchieCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HierarchieCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hierarchieCnCFormService = TestBed.inject(HierarchieCnCFormService);
    hierarchieCnCService = TestBed.inject(HierarchieCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const hierarchieCnC: IHierarchieCnC = { id: 15751 };

      activatedRoute.data = of({ hierarchieCnC });
      comp.ngOnInit();

      expect(comp.hierarchieCnC).toEqual(hierarchieCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHierarchieCnC>>();
      const hierarchieCnC = { id: 21190 };
      jest.spyOn(hierarchieCnCFormService, 'getHierarchieCnC').mockReturnValue(hierarchieCnC);
      jest.spyOn(hierarchieCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hierarchieCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hierarchieCnC }));
      saveSubject.complete();

      // THEN
      expect(hierarchieCnCFormService.getHierarchieCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hierarchieCnCService.update).toHaveBeenCalledWith(expect.objectContaining(hierarchieCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHierarchieCnC>>();
      const hierarchieCnC = { id: 21190 };
      jest.spyOn(hierarchieCnCFormService, 'getHierarchieCnC').mockReturnValue({ id: null });
      jest.spyOn(hierarchieCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hierarchieCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hierarchieCnC }));
      saveSubject.complete();

      // THEN
      expect(hierarchieCnCFormService.getHierarchieCnC).toHaveBeenCalled();
      expect(hierarchieCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHierarchieCnC>>();
      const hierarchieCnC = { id: 21190 };
      jest.spyOn(hierarchieCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hierarchieCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hierarchieCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
