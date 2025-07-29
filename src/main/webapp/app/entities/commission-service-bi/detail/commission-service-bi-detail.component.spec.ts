import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CommissionServiceBIDetailComponent } from './commission-service-bi-detail.component';

describe('CommissionServiceBI Management Detail Component', () => {
  let comp: CommissionServiceBIDetailComponent;
  let fixture: ComponentFixture<CommissionServiceBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommissionServiceBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./commission-service-bi-detail.component').then(m => m.CommissionServiceBIDetailComponent),
              resolve: { commissionServiceBI: () => of({ id: 7638 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CommissionServiceBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionServiceBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load commissionServiceBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CommissionServiceBIDetailComponent);

      // THEN
      expect(instance.commissionServiceBI()).toEqual(expect.objectContaining({ id: 7638 }));
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
