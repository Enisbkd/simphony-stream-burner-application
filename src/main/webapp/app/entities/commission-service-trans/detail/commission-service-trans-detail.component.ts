import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICommissionServiceTrans } from '../commission-service-trans.model';

@Component({
  selector: 'jhi-commission-service-trans-detail',
  templateUrl: './commission-service-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CommissionServiceTransDetailComponent {
  commissionServiceTrans = input<ICommissionServiceTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
