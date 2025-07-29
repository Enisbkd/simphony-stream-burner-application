import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrderChannelService } from '../service/order-channel.service';
import { IOrderChannel } from '../order-channel.model';
import { OrderChannelFormService } from './order-channel-form.service';

import { OrderChannelUpdateComponent } from './order-channel-update.component';

describe('OrderChannel Management Update Component', () => {
  let comp: OrderChannelUpdateComponent;
  let fixture: ComponentFixture<OrderChannelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderChannelFormService: OrderChannelFormService;
  let orderChannelService: OrderChannelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrderChannelUpdateComponent],
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
      .overrideTemplate(OrderChannelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderChannelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderChannelFormService = TestBed.inject(OrderChannelFormService);
    orderChannelService = TestBed.inject(OrderChannelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const orderChannel: IOrderChannel = { id: 20329 };

      activatedRoute.data = of({ orderChannel });
      comp.ngOnInit();

      expect(comp.orderChannel).toEqual(orderChannel);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderChannel>>();
      const orderChannel = { id: 27066 };
      jest.spyOn(orderChannelFormService, 'getOrderChannel').mockReturnValue(orderChannel);
      jest.spyOn(orderChannelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderChannel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderChannel }));
      saveSubject.complete();

      // THEN
      expect(orderChannelFormService.getOrderChannel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderChannelService.update).toHaveBeenCalledWith(expect.objectContaining(orderChannel));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderChannel>>();
      const orderChannel = { id: 27066 };
      jest.spyOn(orderChannelFormService, 'getOrderChannel').mockReturnValue({ id: null });
      jest.spyOn(orderChannelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderChannel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderChannel }));
      saveSubject.complete();

      // THEN
      expect(orderChannelFormService.getOrderChannel).toHaveBeenCalled();
      expect(orderChannelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderChannel>>();
      const orderChannel = { id: 27066 };
      jest.spyOn(orderChannelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderChannel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderChannelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
