import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrderChannelBI } from '../order-channel-bi.model';

@Component({
  selector: 'jhi-order-channel-bi-detail',
  templateUrl: './order-channel-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrderChannelBIDetailComponent {
  orderChannelBI = input<IOrderChannelBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
