import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MenuItemDefinitionsCnCDetailComponent } from './menu-item-definitions-cn-c-detail.component';

describe('MenuItemDefinitionsCnC Management Detail Component', () => {
  let comp: MenuItemDefinitionsCnCDetailComponent;
  let fixture: ComponentFixture<MenuItemDefinitionsCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuItemDefinitionsCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./menu-item-definitions-cn-c-detail.component').then(m => m.MenuItemDefinitionsCnCDetailComponent),
              resolve: { menuItemDefinitionsCnC: () => of({ id: 19209 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MenuItemDefinitionsCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuItemDefinitionsCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load menuItemDefinitionsCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MenuItemDefinitionsCnCDetailComponent);

      // THEN
      expect(instance.menuItemDefinitionsCnC()).toEqual(expect.objectContaining({ id: 19209 }));
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
