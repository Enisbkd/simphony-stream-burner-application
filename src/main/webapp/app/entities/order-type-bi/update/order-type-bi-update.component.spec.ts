import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrderTypeBIService } from '../service/order-type-bi.service';
import { IOrderTypeBI } from '../order-type-bi.model';
import { OrderTypeBIFormService } from './order-type-bi-form.service';

import { OrderTypeBIUpdateComponent } from './order-type-bi-update.component';

describe('OrderTypeBI Management Update Component', () => {
  let comp: OrderTypeBIUpdateComponent;
  let fixture: ComponentFixture<OrderTypeBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderTypeBIFormService: OrderTypeBIFormService;
  let orderTypeBIService: OrderTypeBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrderTypeBIUpdateComponent],
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
      .overrideTemplate(OrderTypeBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderTypeBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderTypeBIFormService = TestBed.inject(OrderTypeBIFormService);
    orderTypeBIService = TestBed.inject(OrderTypeBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const orderTypeBI: IOrderTypeBI = { id: 22507 };

      activatedRoute.data = of({ orderTypeBI });
      comp.ngOnInit();

      expect(comp.orderTypeBI).toEqual(orderTypeBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderTypeBI>>();
      const orderTypeBI = { id: 10594 };
      jest.spyOn(orderTypeBIFormService, 'getOrderTypeBI').mockReturnValue(orderTypeBI);
      jest.spyOn(orderTypeBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderTypeBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderTypeBI }));
      saveSubject.complete();

      // THEN
      expect(orderTypeBIFormService.getOrderTypeBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderTypeBIService.update).toHaveBeenCalledWith(expect.objectContaining(orderTypeBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderTypeBI>>();
      const orderTypeBI = { id: 10594 };
      jest.spyOn(orderTypeBIFormService, 'getOrderTypeBI').mockReturnValue({ id: null });
      jest.spyOn(orderTypeBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderTypeBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderTypeBI }));
      saveSubject.complete();

      // THEN
      expect(orderTypeBIFormService.getOrderTypeBI).toHaveBeenCalled();
      expect(orderTypeBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderTypeBI>>();
      const orderTypeBI = { id: 10594 };
      jest.spyOn(orderTypeBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderTypeBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderTypeBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
