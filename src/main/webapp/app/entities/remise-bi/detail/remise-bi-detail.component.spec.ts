import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RemiseBIDetailComponent } from './remise-bi-detail.component';

describe('RemiseBI Management Detail Component', () => {
  let comp: RemiseBIDetailComponent;
  let fixture: ComponentFixture<RemiseBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemiseBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./remise-bi-detail.component').then(m => m.RemiseBIDetailComponent),
              resolve: { remiseBI: () => of({ id: 31748 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RemiseBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemiseBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load remiseBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RemiseBIDetailComponent);

      // THEN
      expect(instance.remiseBI()).toEqual(expect.objectContaining({ id: 31748 }));
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
