import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BarCodeTransDetailComponent } from './bar-code-trans-detail.component';

describe('BarCodeTrans Management Detail Component', () => {
  let comp: BarCodeTransDetailComponent;
  let fixture: ComponentFixture<BarCodeTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BarCodeTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./bar-code-trans-detail.component').then(m => m.BarCodeTransDetailComponent),
              resolve: { barCodeTrans: () => of({ id: 19267 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BarCodeTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BarCodeTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load barCodeTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BarCodeTransDetailComponent);

      // THEN
      expect(instance.barCodeTrans()).toEqual(expect.objectContaining({ id: 19267 }));
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
