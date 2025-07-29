import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IModePaiementTrans } from '../mode-paiement-trans.model';
import { ModePaiementTransService } from '../service/mode-paiement-trans.service';

@Component({
  templateUrl: './mode-paiement-trans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ModePaiementTransDeleteDialogComponent {
  modePaiementTrans?: IModePaiementTrans;

  protected modePaiementTransService = inject(ModePaiementTransService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modePaiementTransService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
