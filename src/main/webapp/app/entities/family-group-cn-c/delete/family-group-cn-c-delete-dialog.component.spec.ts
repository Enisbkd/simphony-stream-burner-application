jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FamilyGroupCnCService } from '../service/family-group-cn-c.service';

import { FamilyGroupCnCDeleteDialogComponent } from './family-group-cn-c-delete-dialog.component';

describe('FamilyGroupCnC Management Delete Component', () => {
  let comp: FamilyGroupCnCDeleteDialogComponent;
  let fixture: ComponentFixture<FamilyGroupCnCDeleteDialogComponent>;
  let service: FamilyGroupCnCService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FamilyGroupCnCDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FamilyGroupCnCDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FamilyGroupCnCDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FamilyGroupCnCService);
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
