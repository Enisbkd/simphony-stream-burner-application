import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HttpCallAuditDetailComponent } from './http-call-audit-detail.component';

describe('HttpCallAudit Management Detail Component', () => {
  let comp: HttpCallAuditDetailComponent;
  let fixture: ComponentFixture<HttpCallAuditDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpCallAuditDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./http-call-audit-detail.component').then(m => m.HttpCallAuditDetailComponent),
              resolve: { httpCallAudit: () => of({ id: 18456 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HttpCallAuditDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HttpCallAuditDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load httpCallAudit on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HttpCallAuditDetailComponent);

      // THEN
      expect(instance.httpCallAudit()).toEqual(expect.objectContaining({ id: 18456 }));
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
