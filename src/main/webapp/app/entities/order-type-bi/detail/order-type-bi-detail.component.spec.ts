import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrderTypeBIDetailComponent } from './order-type-bi-detail.component';

describe('OrderTypeBI Management Detail Component', () => {
  let comp: OrderTypeBIDetailComponent;
  let fixture: ComponentFixture<OrderTypeBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderTypeBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./order-type-bi-detail.component').then(m => m.OrderTypeBIDetailComponent),
              resolve: { orderTypeBI: () => of({ id: 10594 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrderTypeBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderTypeBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load orderTypeBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrderTypeBIDetailComponent);

      // THEN
      expect(instance.orderTypeBI()).toEqual(expect.objectContaining({ id: 10594 }));
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
