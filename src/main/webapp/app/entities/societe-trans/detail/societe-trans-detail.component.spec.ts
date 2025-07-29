import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SocieteTransDetailComponent } from './societe-trans-detail.component';

describe('SocieteTrans Management Detail Component', () => {
  let comp: SocieteTransDetailComponent;
  let fixture: ComponentFixture<SocieteTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SocieteTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./societe-trans-detail.component').then(m => m.SocieteTransDetailComponent),
              resolve: { societeTrans: () => of({ id: 1464 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SocieteTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocieteTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load societeTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SocieteTransDetailComponent);

      // THEN
      expect(instance.societeTrans()).toEqual(expect.objectContaining({ id: 1464 }));
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
