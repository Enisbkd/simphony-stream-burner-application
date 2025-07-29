import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CheckTransDetailComponent } from './check-trans-detail.component';

describe('CheckTrans Management Detail Component', () => {
  let comp: CheckTransDetailComponent;
  let fixture: ComponentFixture<CheckTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./check-trans-detail.component').then(m => m.CheckTransDetailComponent),
              resolve: { checkTrans: () => of({ id: 2216 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CheckTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load checkTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CheckTransDetailComponent);

      // THEN
      expect(instance.checkTrans()).toEqual(expect.objectContaining({ id: 2216 }));
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
