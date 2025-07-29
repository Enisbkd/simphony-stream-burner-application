import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TaxeClassTransDetailComponent } from './taxe-class-trans-detail.component';

describe('TaxeClassTrans Management Detail Component', () => {
  let comp: TaxeClassTransDetailComponent;
  let fixture: ComponentFixture<TaxeClassTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxeClassTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./taxe-class-trans-detail.component').then(m => m.TaxeClassTransDetailComponent),
              resolve: { taxeClassTrans: () => of({ id: 32691 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TaxeClassTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaxeClassTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load taxeClassTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TaxeClassTransDetailComponent);

      // THEN
      expect(instance.taxeClassTrans()).toEqual(expect.objectContaining({ id: 32691 }));
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
