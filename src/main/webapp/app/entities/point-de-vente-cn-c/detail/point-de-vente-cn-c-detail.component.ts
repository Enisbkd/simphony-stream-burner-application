import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';

@Component({
  selector: 'jhi-point-de-vente-cn-c-detail',
  templateUrl: './point-de-vente-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PointDeVenteCnCDetailComponent {
  pointDeVenteCnC = input<IPointDeVenteCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
