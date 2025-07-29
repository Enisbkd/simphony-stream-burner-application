import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ITaxeRateTrans } from '../taxe-rate-trans.model';

@Component({
  selector: 'jhi-taxe-rate-trans-detail',
  templateUrl: './taxe-rate-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class TaxeRateTransDetailComponent {
  taxeRateTrans = input<ITaxeRateTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
