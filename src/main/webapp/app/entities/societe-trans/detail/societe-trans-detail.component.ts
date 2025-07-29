import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ISocieteTrans } from '../societe-trans.model';

@Component({
  selector: 'jhi-societe-trans-detail',
  templateUrl: './societe-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class SocieteTransDetailComponent {
  societeTrans = input<ISocieteTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
