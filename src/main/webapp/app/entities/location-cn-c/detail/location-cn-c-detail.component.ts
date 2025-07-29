import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ILocationCnC } from '../location-cn-c.model';

@Component({
  selector: 'jhi-location-cn-c-detail',
  templateUrl: './location-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class LocationCnCDetailComponent {
  locationCnC = input<ILocationCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
