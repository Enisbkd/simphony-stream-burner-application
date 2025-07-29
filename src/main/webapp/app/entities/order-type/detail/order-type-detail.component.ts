import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrderType } from '../order-type.model';

@Component({
  selector: 'jhi-order-type-detail',
  templateUrl: './order-type-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrderTypeDetailComponent {
  orderType = input<IOrderType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
