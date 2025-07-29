import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MenuItemMastersCnCDetailComponent } from './menu-item-masters-cn-c-detail.component';

describe('MenuItemMastersCnC Management Detail Component', () => {
  let comp: MenuItemMastersCnCDetailComponent;
  let fixture: ComponentFixture<MenuItemMastersCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuItemMastersCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./menu-item-masters-cn-c-detail.component').then(m => m.MenuItemMastersCnCDetailComponent),
              resolve: { menuItemMastersCnC: () => of({ id: 27918 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MenuItemMastersCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuItemMastersCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load menuItemMastersCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MenuItemMastersCnCDetailComponent);

      // THEN
      expect(instance.menuItemMastersCnC()).toEqual(expect.objectContaining({ id: 27918 }));
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
