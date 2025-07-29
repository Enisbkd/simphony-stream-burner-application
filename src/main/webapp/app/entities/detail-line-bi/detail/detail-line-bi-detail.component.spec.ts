import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DetailLineBIDetailComponent } from './detail-line-bi-detail.component';

describe('DetailLineBI Management Detail Component', () => {
  let comp: DetailLineBIDetailComponent;
  let fixture: ComponentFixture<DetailLineBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailLineBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./detail-line-bi-detail.component').then(m => m.DetailLineBIDetailComponent),
              resolve: { detailLineBI: () => of({ id: 8196 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DetailLineBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailLineBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load detailLineBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DetailLineBIDetailComponent);

      // THEN
      expect(instance.detailLineBI()).toEqual(expect.objectContaining({ id: 8196 }));
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
