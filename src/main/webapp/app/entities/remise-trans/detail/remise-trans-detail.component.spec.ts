import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RemiseTransDetailComponent } from './remise-trans-detail.component';

describe('RemiseTrans Management Detail Component', () => {
  let comp: RemiseTransDetailComponent;
  let fixture: ComponentFixture<RemiseTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemiseTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./remise-trans-detail.component').then(m => m.RemiseTransDetailComponent),
              resolve: { remiseTrans: () => of({ id: 19837 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RemiseTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemiseTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load remiseTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RemiseTransDetailComponent);

      // THEN
      expect(instance.remiseTrans()).toEqual(expect.objectContaining({ id: 19837 }));
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
