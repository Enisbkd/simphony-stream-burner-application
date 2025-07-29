import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ModePaiementTransDetailComponent } from './mode-paiement-trans-detail.component';

describe('ModePaiementTrans Management Detail Component', () => {
  let comp: ModePaiementTransDetailComponent;
  let fixture: ComponentFixture<ModePaiementTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModePaiementTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./mode-paiement-trans-detail.component').then(m => m.ModePaiementTransDetailComponent),
              resolve: { modePaiementTrans: () => of({ id: 15721 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ModePaiementTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModePaiementTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load modePaiementTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ModePaiementTransDetailComponent);

      // THEN
      expect(instance.modePaiementTrans()).toEqual(expect.objectContaining({ id: 15721 }));
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
