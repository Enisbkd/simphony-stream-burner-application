import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITaxeRateTrans } from '../taxe-rate-trans.model';
import { TaxeRateTransService } from '../service/taxe-rate-trans.service';

@Component({
  templateUrl: './taxe-rate-trans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TaxeRateTransDeleteDialogComponent {
  taxeRateTrans?: ITaxeRateTrans;

  protected taxeRateTransService = inject(TaxeRateTransService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxeRateTransService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
