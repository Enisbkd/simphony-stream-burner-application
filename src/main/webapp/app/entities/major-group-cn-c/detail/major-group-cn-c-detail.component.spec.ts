import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MajorGroupCnCDetailComponent } from './major-group-cn-c-detail.component';

describe('MajorGroupCnC Management Detail Component', () => {
  let comp: MajorGroupCnCDetailComponent;
  let fixture: ComponentFixture<MajorGroupCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MajorGroupCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./major-group-cn-c-detail.component').then(m => m.MajorGroupCnCDetailComponent),
              resolve: { majorGroupCnC: () => of({ id: 17824 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MajorGroupCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MajorGroupCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load majorGroupCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MajorGroupCnCDetailComponent);

      // THEN
      expect(instance.majorGroupCnC()).toEqual(expect.objectContaining({ id: 17824 }));
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
