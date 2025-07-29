import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ITaxeClassTrans } from '../taxe-class-trans.model';

@Component({
  selector: 'jhi-taxe-class-trans-detail',
  templateUrl: './taxe-class-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class TaxeClassTransDetailComponent {
  taxeClassTrans = input<ITaxeClassTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
