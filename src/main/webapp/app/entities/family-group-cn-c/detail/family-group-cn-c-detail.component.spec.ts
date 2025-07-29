import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FamilyGroupCnCDetailComponent } from './family-group-cn-c-detail.component';

describe('FamilyGroupCnC Management Detail Component', () => {
  let comp: FamilyGroupCnCDetailComponent;
  let fixture: ComponentFixture<FamilyGroupCnCDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FamilyGroupCnCDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./family-group-cn-c-detail.component').then(m => m.FamilyGroupCnCDetailComponent),
              resolve: { familyGroupCnC: () => of({ id: 16235 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FamilyGroupCnCDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FamilyGroupCnCDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load familyGroupCnC on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FamilyGroupCnCDetailComponent);

      // THEN
      expect(instance.familyGroupCnC()).toEqual(expect.objectContaining({ id: 16235 }));
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
