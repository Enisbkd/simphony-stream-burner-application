import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrganizationLocationTransDetailComponent } from './organization-location-trans-detail.component';

describe('OrganizationLocationTrans Management Detail Component', () => {
  let comp: OrganizationLocationTransDetailComponent;
  let fixture: ComponentFixture<OrganizationLocationTransDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrganizationLocationTransDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./organization-location-trans-detail.component').then(m => m.OrganizationLocationTransDetailComponent),
              resolve: { organizationLocationTrans: () => of({ id: 30457 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrganizationLocationTransDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizationLocationTransDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load organizationLocationTrans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrganizationLocationTransDetailComponent);

      // THEN
      expect(instance.organizationLocationTrans()).toEqual(expect.objectContaining({ id: 30457 }));
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
