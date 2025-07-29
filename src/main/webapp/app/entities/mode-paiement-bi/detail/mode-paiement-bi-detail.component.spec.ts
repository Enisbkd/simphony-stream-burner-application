import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ModePaiementBIDetailComponent } from './mode-paiement-bi-detail.component';

describe('ModePaiementBI Management Detail Component', () => {
  let comp: ModePaiementBIDetailComponent;
  let fixture: ComponentFixture<ModePaiementBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModePaiementBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./mode-paiement-bi-detail.component').then(m => m.ModePaiementBIDetailComponent),
              resolve: { modePaiementBI: () => of({ id: 25413 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ModePaiementBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModePaiementBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load modePaiementBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ModePaiementBIDetailComponent);

      // THEN
      expect(instance.modePaiementBI()).toEqual(expect.objectContaining({ id: 25413 }));
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
