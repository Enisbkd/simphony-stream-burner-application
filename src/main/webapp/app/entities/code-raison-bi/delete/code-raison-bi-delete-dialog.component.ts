import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICodeRaisonBI } from '../code-raison-bi.model';
import { CodeRaisonBIService } from '../service/code-raison-bi.service';

@Component({
  templateUrl: './code-raison-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CodeRaisonBIDeleteDialogComponent {
  codeRaisonBI?: ICodeRaisonBI;

  protected codeRaisonBIService = inject(CodeRaisonBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codeRaisonBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
