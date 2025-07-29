import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IModePaiementBI } from '../mode-paiement-bi.model';
import { ModePaiementBIService } from '../service/mode-paiement-bi.service';

@Component({
  templateUrl: './mode-paiement-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ModePaiementBIDeleteDialogComponent {
  modePaiementBI?: IModePaiementBI;

  protected modePaiementBIService = inject(ModePaiementBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modePaiementBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
