import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IModePaiementBI } from '../mode-paiement-bi.model';

@Component({
  selector: 'jhi-mode-paiement-bi-detail',
  templateUrl: './mode-paiement-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ModePaiementBIDetailComponent {
  modePaiementBI = input<IModePaiementBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
