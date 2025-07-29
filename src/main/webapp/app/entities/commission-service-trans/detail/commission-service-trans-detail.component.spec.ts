import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CommissionServiceTransDetailComponent } from './commission-service-trans-detail.component';

describe('CommissionServiceTrans Management Detail Component', () => {
  let comp: CommissionServiceTransDetailComponent;
  let fixture: ComponentFixture<CommissionServiceTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommissionServiceTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./commission-service-trans-detail.component').then(m => m.CommissionServiceTransDetailComponent),
              resolve: { commissionServiceTrans: () => of({ id: 29235 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CommissionServiceTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionServiceTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load commissionServiceTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CommissionServiceTransDetailComponent);

      // THEN
      expect(instance.commissionServiceTrans()).toEqual(expect.objectContaining({ id: 29235 }));
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
