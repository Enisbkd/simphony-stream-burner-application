import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IModePaiementTrans } from '../mode-paiement-trans.model';

@Component({
  selector: 'jhi-mode-paiement-trans-detail',
  templateUrl: './mode-paiement-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ModePaiementTransDetailComponent {
  modePaiementTrans = input<IModePaiementTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
