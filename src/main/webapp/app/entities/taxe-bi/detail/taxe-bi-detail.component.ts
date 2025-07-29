import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ITaxeBI } from '../taxe-bi.model';

@Component({
  selector: 'jhi-taxe-bi-detail',
  templateUrl: './taxe-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class TaxeBIDetailComponent {
  taxeBI = input<ITaxeBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
