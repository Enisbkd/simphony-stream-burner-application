import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IDetailLineBI } from '../detail-line-bi.model';

@Component({
  selector: 'jhi-detail-line-bi-detail',
  templateUrl: './detail-line-bi-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class DetailLineBIDetailComponent {
  detailLineBI = input<IDetailLineBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
