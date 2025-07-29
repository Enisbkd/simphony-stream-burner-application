import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CommissionServiceBIService } from '../service/commission-service-bi.service';
import { ICommissionServiceBI } from '../commission-service-bi.model';
import { CommissionServiceBIFormService } from './commission-service-bi-form.service';

import { CommissionServiceBIUpdateComponent } from './commission-service-bi-update.component';

describe('CommissionServiceBI Management Update Component', () => {
  let comp: CommissionServiceBIUpdateComponent;
  let fixture: ComponentFixture<CommissionServiceBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commissionServiceBIFormService: CommissionServiceBIFormService;
  let commissionServiceBIService: CommissionServiceBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CommissionServiceBIUpdateComponent],
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
      .overrideTemplate(CommissionServiceBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommissionServiceBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commissionServiceBIFormService = TestBed.inject(CommissionServiceBIFormService);
    commissionServiceBIService = TestBed.inject(CommissionServiceBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const commissionServiceBI: ICommissionServiceBI = { id: 32191 };

      activatedRoute.data = of({ commissionServiceBI });
      comp.ngOnInit();

      expect(comp.commissionServiceBI).toEqual(commissionServiceBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionServiceBI>>();
      const commissionServiceBI = { id: 7638 };
      jest.spyOn(commissionServiceBIFormService, 'getCommissionServiceBI').mockReturnValue(commissionServiceBI);
      jest.spyOn(commissionServiceBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionServiceBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commissionServiceBI }));
      saveSubject.complete();

      // THEN
      expect(commissionServiceBIFormService.getCommissionServiceBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commissionServiceBIService.update).toHaveBeenCalledWith(expect.objectContaining(commissionServiceBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionServiceBI>>();
      const commissionServiceBI = { id: 7638 };
      jest.spyOn(commissionServiceBIFormService, 'getCommissionServiceBI').mockReturnValue({ id: null });
      jest.spyOn(commissionServiceBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionServiceBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commissionServiceBI }));
      saveSubject.complete();

      // THEN
      expect(commissionServiceBIFormService.getCommissionServiceBI).toHaveBeenCalled();
      expect(commissionServiceBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionServiceBI>>();
      const commissionServiceBI = { id: 7638 };
      jest.spyOn(commissionServiceBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionServiceBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commissionServiceBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
