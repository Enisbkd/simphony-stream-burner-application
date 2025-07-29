import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrderChannelBIService } from '../service/order-channel-bi.service';
import { IOrderChannelBI } from '../order-channel-bi.model';
import { OrderChannelBIFormService } from './order-channel-bi-form.service';

import { OrderChannelBIUpdateComponent } from './order-channel-bi-update.component';

describe('OrderChannelBI Management Update Component', () => {
  let comp: OrderChannelBIUpdateComponent;
  let fixture: ComponentFixture<OrderChannelBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderChannelBIFormService: OrderChannelBIFormService;
  let orderChannelBIService: OrderChannelBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrderChannelBIUpdateComponent],
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
      .overrideTemplate(OrderChannelBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderChannelBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderChannelBIFormService = TestBed.inject(OrderChannelBIFormService);
    orderChannelBIService = TestBed.inject(OrderChannelBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const orderChannelBI: IOrderChannelBI = { id: 3386 };

      activatedRoute.data = of({ orderChannelBI });
      comp.ngOnInit();

      expect(comp.orderChannelBI).toEqual(orderChannelBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderChannelBI>>();
      const orderChannelBI = { id: 32611 };
      jest.spyOn(orderChannelBIFormService, 'getOrderChannelBI').mockReturnValue(orderChannelBI);
      jest.spyOn(orderChannelBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderChannelBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderChannelBI }));
      saveSubject.complete();

      // THEN
      expect(orderChannelBIFormService.getOrderChannelBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderChannelBIService.update).toHaveBeenCalledWith(expect.objectContaining(orderChannelBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderChannelBI>>();
      const orderChannelBI = { id: 32611 };
      jest.spyOn(orderChannelBIFormService, 'getOrderChannelBI').mockReturnValue({ id: null });
      jest.spyOn(orderChannelBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderChannelBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderChannelBI }));
      saveSubject.complete();

      // THEN
      expect(orderChannelBIFormService.getOrderChannelBI).toHaveBeenCalled();
      expect(orderChannelBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderChannelBI>>();
      const orderChannelBI = { id: 32611 };
      jest.spyOn(orderChannelBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderChannelBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderChannelBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
