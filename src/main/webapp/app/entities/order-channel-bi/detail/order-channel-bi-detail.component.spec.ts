import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrderChannelBIDetailComponent } from './order-channel-bi-detail.component';

describe('OrderChannelBI Management Detail Component', () => {
  let comp: OrderChannelBIDetailComponent;
  let fixture: ComponentFixture<OrderChannelBIDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderChannelBIDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./order-channel-bi-detail.component').then(m => m.OrderChannelBIDetailComponent),
              resolve: { orderChannelBI: () => of({ id: 32611 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrderChannelBIDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderChannelBIDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load orderChannelBI on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrderChannelBIDetailComponent);

      // THEN
      expect(instance.orderChannelBI()).toEqual(expect.objectContaining({ id: 32611 }));
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
