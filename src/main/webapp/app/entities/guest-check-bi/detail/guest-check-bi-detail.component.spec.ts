import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GuestCheckBIDetailComponent } from './guest-check-bi-detail.component';

describe('GuestCheckBI Management Detail Component', () => {
  let comp: GuestCheckBIDetailComponent;
  let fixture: ComponentFixture<GuestCheckBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestCheckBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./guest-check-bi-detail.component').then(m => m.GuestCheckBIDetailComponent),
              resolve: { guestCheckBI: () => of({ id: 10485 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GuestCheckBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestCheckBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load guestCheckBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GuestCheckBIDetailComponent);

      // THEN
      expect(instance.guestCheckBI()).toEqual(expect.objectContaining({ id: 10485 }));
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
