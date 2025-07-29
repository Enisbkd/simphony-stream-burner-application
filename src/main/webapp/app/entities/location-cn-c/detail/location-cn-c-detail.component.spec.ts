import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { LocationCnCDetailComponent } from './location-cn-c-detail.component';

describe('LocationCnC Management Detail Component', () => {
  let comp: LocationCnCDetailComponent;
  let fixture: ComponentFixture<LocationCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LocationCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./location-cn-c-detail.component').then(m => m.LocationCnCDetailComponent),
              resolve: { locationCnC: () => of({ id: 3212 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LocationCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load locationCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LocationCnCDetailComponent);

      // THEN
      expect(instance.locationCnC()).toEqual(expect.objectContaining({ id: 3212 }));
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
