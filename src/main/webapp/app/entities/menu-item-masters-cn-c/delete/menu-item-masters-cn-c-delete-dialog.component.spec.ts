jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MenuItemMastersCnCService } from '../service/menu-item-masters-cn-c.service';

import { MenuItemMastersCnCDeleteDialogComponent } from './menu-item-masters-cn-c-delete-dialog.component';

describe('MenuItemMastersCnC Management Delete Component', () => {
  let comp: MenuItemMastersCnCDeleteDialogComponent;
  let fixture: ComponentFixture<MenuItemMastersCnCDeleteDialogComponent>;
  let service: MenuItemMastersCnCService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MenuItemMastersCnCDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(MenuItemMastersCnCDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MenuItemMastersCnCDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MenuItemMastersCnCService);
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
