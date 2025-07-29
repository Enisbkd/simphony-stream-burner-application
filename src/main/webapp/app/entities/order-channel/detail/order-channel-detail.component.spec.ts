import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrderChannelDetailComponent } from './order-channel-detail.component';

describe('OrderChannel Management Detail Component', () => {
  let comp: OrderChannelDetailComponent;
  let fixture: ComponentFixture<OrderChannelDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderChannelDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./order-channel-detail.component').then(m => m.OrderChannelDetailComponent),
              resolve: { orderChannel: () => of({ id: 27066 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrderChannelDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderChannelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load orderChannel on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrderChannelDetailComponent);

      // THEN
      expect(instance.orderChannel()).toEqual(expect.objectContaining({ id: 27066 }));
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
