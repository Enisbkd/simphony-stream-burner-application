import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TaxeRateTransDetailComponent } from './taxe-rate-trans-detail.component';

describe('TaxeRateTrans Management Detail Component', () => {
  let comp: TaxeRateTransDetailComponent;
  let fixture: ComponentFixture<TaxeRateTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxeRateTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./taxe-rate-trans-detail.component').then(m => m.TaxeRateTransDetailComponent),
              resolve: { taxeRateTrans: () => of({ id: 7322 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TaxeRateTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaxeRateTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load taxeRateTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TaxeRateTransDetailComponent);

      // THEN
      expect(instance.taxeRateTrans()).toEqual(expect.objectContaining({ id: 7322 }));
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
