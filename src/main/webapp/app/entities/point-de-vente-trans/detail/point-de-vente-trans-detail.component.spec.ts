import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PointDeVenteTransDetailComponent } from './point-de-vente-trans-detail.component';

describe('PointDeVenteTrans Management Detail Component', () => {
  let comp: PointDeVenteTransDetailComponent;
  let fixture: ComponentFixture<PointDeVenteTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PointDeVenteTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./point-de-vente-trans-detail.component').then(m => m.PointDeVenteTransDetailComponent),
              resolve: { pointDeVenteTrans: () => of({ id: 3768 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PointDeVenteTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointDeVenteTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load pointDeVenteTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PointDeVenteTransDetailComponent);

      // THEN
      expect(instance.pointDeVenteTrans()).toEqual(expect.objectContaining({ id: 3768 }));
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
