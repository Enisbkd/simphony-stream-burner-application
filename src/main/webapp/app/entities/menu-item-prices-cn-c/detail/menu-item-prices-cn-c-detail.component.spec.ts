import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MenuItemPricesCnCDetailComponent } from './menu-item-prices-cn-c-detail.component';

describe('MenuItemPricesCnC Management Detail Component', () => {
  let comp: MenuItemPricesCnCDetailComponent;
  let fixture: ComponentFixture<MenuItemPricesCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuItemPricesCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./menu-item-prices-cn-c-detail.component').then(m => m.MenuItemPricesCnCDetailComponent),
              resolve: { menuItemPricesCnC: () => of({ id: 11591 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MenuItemPricesCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuItemPricesCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load menuItemPricesCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MenuItemPricesCnCDetailComponent);

      // THEN
      expect(instance.menuItemPricesCnC()).toEqual(expect.objectContaining({ id: 11591 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
