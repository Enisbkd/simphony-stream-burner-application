jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CommissionServiceBIService } from '../service/commission-service-bi.service';

import { CommissionServiceBIDeleteDialogComponent } from './commission-service-bi-delete-dialog.component';

describe('CommissionServiceBI Management Delete Component', () => {
  let comp: CommissionServiceBIDeleteDialogComponent;
  let fixture: ComponentFixture<CommissionServiceBIDeleteDialogComponent>;
  let service: CommissionServiceBIService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CommissionServiceBIDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(CommissionServiceBIDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommissionServiceBIDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CommissionServiceBIService);
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
