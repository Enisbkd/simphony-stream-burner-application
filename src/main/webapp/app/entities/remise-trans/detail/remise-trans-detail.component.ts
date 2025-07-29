import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IRemiseTrans } from '../remise-trans.model';

@Component({
  selector: 'jhi-remise-trans-detail',
  templateUrl: './remise-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class RemiseTransDetailComponent {
  remiseTrans = input<IRemiseTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
