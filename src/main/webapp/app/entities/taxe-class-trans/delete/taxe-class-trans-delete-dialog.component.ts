import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITaxeClassTrans } from '../taxe-class-trans.model';
import { TaxeClassTransService } from '../service/taxe-class-trans.service';

@Component({
  templateUrl: './taxe-class-trans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TaxeClassTransDeleteDialogComponent {
  taxeClassTrans?: ITaxeClassTrans;

  protected taxeClassTransService = inject(TaxeClassTransService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxeClassTransService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
