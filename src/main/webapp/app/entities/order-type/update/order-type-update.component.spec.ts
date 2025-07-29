import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrderTypeService } from '../service/order-type.service';
import { IOrderType } from '../order-type.model';
import { OrderTypeFormService } from './order-type-form.service';

import { OrderTypeUpdateComponent } from './order-type-update.component';

describe('OrderType Management Update Component', () => {
  let comp: OrderTypeUpdateComponent;
  let fixture: ComponentFixture<OrderTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderTypeFormService: OrderTypeFormService;
  let orderTypeService: OrderTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrderTypeUpdateComponent],
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
      .overrideTemplate(OrderTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderTypeFormService = TestBed.inject(OrderTypeFormService);
    orderTypeService = TestBed.inject(OrderTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const orderType: IOrderType = { id: 28377 };

      activatedRoute.data = of({ orderType });
      comp.ngOnInit();

      expect(comp.orderType).toEqual(orderType);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderType>>();
      const orderType = { id: 10129 };
      jest.spyOn(orderTypeFormService, 'getOrderType').mockReturnValue(orderType);
      jest.spyOn(orderTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderType }));
      saveSubject.complete();

      // THEN
      expect(orderTypeFormService.getOrderType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderTypeService.update).toHaveBeenCalledWith(expect.objectContaining(orderType));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderType>>();
      const orderType = { id: 10129 };
      jest.spyOn(orderTypeFormService, 'getOrderType').mockReturnValue({ id: null });
      jest.spyOn(orderTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderType }));
      saveSubject.complete();

      // THEN
      expect(orderTypeFormService.getOrderType).toHaveBeenCalled();
      expect(orderTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderType>>();
      const orderType = { id: 10129 };
      jest.spyOn(orderTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
