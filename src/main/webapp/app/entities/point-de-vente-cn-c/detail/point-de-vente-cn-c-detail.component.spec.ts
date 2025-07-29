import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PointDeVenteCnCDetailComponent } from './point-de-vente-cn-c-detail.component';

describe('PointDeVenteCnC Management Detail Component', () => {
  let comp: PointDeVenteCnCDetailComponent;
  let fixture: ComponentFixture<PointDeVenteCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PointDeVenteCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./point-de-vente-cn-c-detail.component').then(m => m.PointDeVenteCnCDetailComponent),
              resolve: { pointDeVenteCnC: () => of({ id: 11208 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PointDeVenteCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointDeVenteCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load pointDeVenteCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PointDeVenteCnCDetailComponent);

      // THEN
      expect(instance.pointDeVenteCnC()).toEqual(expect.objectContaining({ id: 11208 }));
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
