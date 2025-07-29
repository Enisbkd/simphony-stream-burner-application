import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmployeeCnCDetailComponent } from './employee-cn-c-detail.component';

describe('EmployeeCnC Management Detail Component', () => {
  let comp: EmployeeCnCDetailComponent;
  let fixture: ComponentFixture<EmployeeCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./employee-cn-c-detail.component').then(m => m.EmployeeCnCDetailComponent),
              resolve: { employeeCnC: () => of({ id: 8307 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmployeeCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load employeeCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmployeeCnCDetailComponent);

      // THEN
      expect(instance.employeeCnC()).toEqual(expect.objectContaining({ id: 8307 }));
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
