import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITaxeBI } from '../taxe-bi.model';
import { TaxeBIService } from '../service/taxe-bi.service';

@Component({
  templateUrl: './taxe-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TaxeBIDeleteDialogComponent {
  taxeBI?: ITaxeBI;

  protected taxeBIService = inject(TaxeBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxeBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
