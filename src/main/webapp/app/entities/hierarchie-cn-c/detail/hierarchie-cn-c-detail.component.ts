import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IHierarchieCnC } from '../hierarchie-cn-c.model';

@Component({
  selector: 'jhi-hierarchie-cn-c-detail',
  templateUrl: './hierarchie-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class HierarchieCnCDetailComponent {
  hierarchieCnC = input<IHierarchieCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
