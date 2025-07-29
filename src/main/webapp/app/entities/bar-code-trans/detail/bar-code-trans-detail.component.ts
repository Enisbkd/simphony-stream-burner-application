import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IBarCodeTrans } from '../bar-code-trans.model';

@Component({
  selector: 'jhi-bar-code-trans-detail',
  templateUrl: './bar-code-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class BarCodeTransDetailComponent {
  barCodeTrans = input<IBarCodeTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
