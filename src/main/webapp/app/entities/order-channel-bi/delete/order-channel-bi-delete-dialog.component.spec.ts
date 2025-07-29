jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { OrderChannelBIService } from '../service/order-channel-bi.service';

import { OrderChannelBIDeleteDialogComponent } from './order-channel-bi-delete-dialog.component';

describe('OrderChannelBI Management Delete Component', () => {
  let comp: OrderChannelBIDeleteDialogComponent;
  let fixture: ComponentFixture<OrderChannelBIDeleteDialogComponent>;
  let service: OrderChannelBIService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrderChannelBIDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(OrderChannelBIDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrderChannelBIDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OrderChannelBIService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
