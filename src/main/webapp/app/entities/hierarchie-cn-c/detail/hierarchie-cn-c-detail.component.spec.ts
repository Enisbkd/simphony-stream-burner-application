import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HierarchieCnCDetailComponent } from './hierarchie-cn-c-detail.component';

describe('HierarchieCnC Management Detail Component', () => {
  let comp: HierarchieCnCDetailComponent;
  let fixture: ComponentFixture<HierarchieCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HierarchieCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./hierarchie-cn-c-detail.component').then(m => m.HierarchieCnCDetailComponent),
              resolve: { hierarchieCnC: () => of({ id: 21190 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HierarchieCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HierarchieCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load hierarchieCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HierarchieCnCDetailComponent);

      // THEN
      expect(instance.hierarchieCnC()).toEqual(expect.objectContaining({ id: 21190 }));
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
