import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrderChannel } from '../order-channel.model';

@Component({
  selector: 'jhi-order-channel-detail',
  templateUrl: './order-channel-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrderChannelDetailComponent {
  orderChannel = input<IOrderChannel | null>(null);

  previousState(): void {
    window.history.back();
  }
}
