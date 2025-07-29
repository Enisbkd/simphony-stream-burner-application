import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrderTypeBI } from '../order-type-bi.model';

@Component({
  selector: 'jhi-order-type-bi-detail',
  templateUrl: './order-type-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrderTypeBIDetailComponent {
  orderTypeBI = input<IOrderTypeBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
