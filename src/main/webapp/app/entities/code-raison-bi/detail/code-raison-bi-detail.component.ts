import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICodeRaisonBI } from '../code-raison-bi.model';

@Component({
  selector: 'jhi-code-raison-bi-detail',
  templateUrl: './code-raison-bi-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CodeRaisonBIDetailComponent {
  codeRaisonBI = input<ICodeRaisonBI | null>(null);

  previousState(): void {
    window.history.back();
  }
}
