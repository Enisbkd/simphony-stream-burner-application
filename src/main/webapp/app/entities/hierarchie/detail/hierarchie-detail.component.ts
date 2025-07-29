import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IHierarchie } from '../hierarchie.model';

@Component({
  selector: 'jhi-hierarchie-detail',
  templateUrl: './hierarchie-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class HierarchieDetailComponent {
  hierarchie = input<IHierarchie | null>(null);

  previousState(): void {
    window.history.back();
  }
}
