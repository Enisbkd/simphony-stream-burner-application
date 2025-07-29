import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { ICheckTrans } from '../check-trans.model';

@Component({
  selector: 'jhi-check-trans-detail',
  templateUrl: './check-trans-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class CheckTransDetailComponent {
  checkTrans = input<ICheckTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
