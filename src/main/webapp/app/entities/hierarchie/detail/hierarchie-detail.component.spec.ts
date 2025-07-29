import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HierarchieDetailComponent } from './hierarchie-detail.component';

describe('Hierarchie Management Detail Component', () => {
  let comp: HierarchieDetailComponent;
  let fixture: ComponentFixture<HierarchieDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HierarchieDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./hierarchie-detail.component').then(m => m.HierarchieDetailComponent),
              resolve: { hierarchie: () => of({ id: 24054 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HierarchieDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HierarchieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load hierarchie on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HierarchieDetailComponent);

      // THEN
      expect(instance.hierarchie()).toEqual(expect.objectContaining({ id: 24054 }));
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
