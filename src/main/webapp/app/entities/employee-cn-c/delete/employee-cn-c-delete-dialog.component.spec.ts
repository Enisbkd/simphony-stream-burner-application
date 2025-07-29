jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EmployeeCnCService } from '../service/employee-cn-c.service';

import { EmployeeCnCDeleteDialogComponent } from './employee-cn-c-delete-dialog.component';

describe('EmployeeCnC Management Delete Component', () => {
  let comp: EmployeeCnCDeleteDialogComponent;
  let fixture: ComponentFixture<EmployeeCnCDeleteDialogComponent>;
  let service: EmployeeCnCService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmployeeCnCDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(EmployeeCnCDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmployeeCnCDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EmployeeCnCService);
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
