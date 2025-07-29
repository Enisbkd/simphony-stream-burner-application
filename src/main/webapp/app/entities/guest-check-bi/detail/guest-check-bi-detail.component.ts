import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IGuestCheckBI } from '../guest-check-bi.model';

@Component({
  selector: 'jhi-guest-check-bi-detail',
  templateUrl: './guest-check-bi-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class GuestCheckBIDetailComponent {
  guestCheckBI = input<IGuestCheckBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
