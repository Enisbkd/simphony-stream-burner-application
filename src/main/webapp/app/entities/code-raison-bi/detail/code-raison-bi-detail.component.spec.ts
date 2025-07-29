import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CodeRaisonBIDetailComponent } from './code-raison-bi-detail.component';

describe('CodeRaisonBI Management Detail Component', () => {
  let comp: CodeRaisonBIDetailComponent;
  let fixture: ComponentFixture<CodeRaisonBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeRaisonBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./code-raison-bi-detail.component').then(m => m.CodeRaisonBIDetailComponent),
              resolve: { codeRaisonBI: () => of({ id: 11250 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CodeRaisonBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeRaisonBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load codeRaisonBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CodeRaisonBIDetailComponent);

      // THEN
      expect(instance.codeRaisonBI()).toEqual(expect.objectContaining({ id: 11250 }));
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
