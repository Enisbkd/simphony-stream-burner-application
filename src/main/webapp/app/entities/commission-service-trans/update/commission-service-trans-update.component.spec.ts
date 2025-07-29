import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CommissionServiceTransService } from '../service/commission-service-trans.service';
import { ICommissionServiceTrans } from '../commission-service-trans.model';
import { CommissionServiceTransFormService } from './commission-service-trans-form.service';

import { CommissionServiceTransUpdateComponent } from './commission-service-trans-update.component';

describe('CommissionServiceTrans Management Update Component', () => {
  let comp: CommissionServiceTransUpdateComponent;
  let fixture: ComponentFixture<CommissionServiceTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commissionServiceTransFormService: CommissionServiceTransFormService;
  let commissionServiceTransService: CommissionServiceTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CommissionServiceTransUpdateComponent],
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
      .overrideTemplate(CommissionServiceTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommissionServiceTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commissionServiceTransFormService = TestBed.inject(CommissionServiceTransFormService);
    commissionServiceTransService = TestBed.inject(CommissionServiceTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const commissionServiceTrans: ICommissionServiceTrans = { id: 12705 };

      activatedRoute.data = of({ commissionServiceTrans });
      comp.ngOnInit();

      expect(comp.commissionServiceTrans).toEqual(commissionServiceTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionServiceTrans>>();
      const commissionServiceTrans = { id: 29235 };
      jest.spyOn(commissionServiceTransFormService, 'getCommissionServiceTrans').mockReturnValue(commissionServiceTrans);
      jest.spyOn(commissionServiceTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionServiceTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commissionServiceTrans }));
      saveSubject.complete();

      // THEN
      expect(commissionServiceTransFormService.getCommissionServiceTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commissionServiceTransService.update).toHaveBeenCalledWith(expect.objectContaining(commissionServiceTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionServiceTrans>>();
      const commissionServiceTrans = { id: 29235 };
      jest.spyOn(commissionServiceTransFormService, 'getCommissionServiceTrans').mockReturnValue({ id: null });
      jest.spyOn(commissionServiceTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionServiceTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commissionServiceTrans }));
      saveSubject.complete();

      // THEN
      expect(commissionServiceTransFormService.getCommissionServiceTrans).toHaveBeenCalled();
      expect(commissionServiceTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionServiceTrans>>();
      const commissionServiceTrans = { id: 29235 };
      jest.spyOn(commissionServiceTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionServiceTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commissionServiceTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
