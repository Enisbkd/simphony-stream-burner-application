import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IRemiseBI } from '../remise-bi.model';

@Component({
  selector: 'jhi-remise-bi-detail',
  templateUrl: './remise-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class RemiseBIDetailComponent {
  remiseBI = input<IRemiseBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
