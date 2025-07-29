import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TaxeBIDetailComponent } from './taxe-bi-detail.component';

describe('TaxeBI Management Detail Component', () => {
  let comp: TaxeBIDetailComponent;
  let fixture: ComponentFixture<TaxeBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxeBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./taxe-bi-detail.component').then(m => m.TaxeBIDetailComponent),
              resolve: { taxeBI: () => of({ id: 18086 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TaxeBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaxeBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load taxeBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TaxeBIDetailComponent);

      // THEN
      expect(instance.taxeBI()).toEqual(expect.objectContaining({ id: 18086 }));
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
