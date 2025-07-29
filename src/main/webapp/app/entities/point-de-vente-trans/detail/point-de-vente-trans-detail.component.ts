import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPointDeVenteTrans } from '../point-de-vente-trans.model';

@Component({
  selector: 'jhi-point-de-vente-trans-detail',
  templateUrl: './point-de-vente-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PointDeVenteTransDetailComponent {
  pointDeVenteTrans = input<IPointDeVenteTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
