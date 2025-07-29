jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MenuItemPricesCnCService } from '../service/menu-item-prices-cn-c.service';

import { MenuItemPricesCnCDeleteDialogComponent } from './menu-item-prices-cn-c-delete-dialog.component';

describe('MenuItemPricesCnC Management Delete Component', () => {
  let comp: MenuItemPricesCnCDeleteDialogComponent;
  let fixture: ComponentFixture<MenuItemPricesCnCDeleteDialogComponent>;
  let service: MenuItemPricesCnCService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MenuItemPricesCnCDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(MenuItemPricesCnCDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MenuItemPricesCnCDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MenuItemPricesCnCService);
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
