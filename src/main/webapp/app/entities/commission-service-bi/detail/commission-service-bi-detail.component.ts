import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICommissionServiceBI } from '../commission-service-bi.model';

@Component({
  selector: 'jhi-commission-service-bi-detail',
  templateUrl: './commission-service-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CommissionServiceBIDetailComponent {
  commissionServiceBI = input<ICommissionServiceBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
