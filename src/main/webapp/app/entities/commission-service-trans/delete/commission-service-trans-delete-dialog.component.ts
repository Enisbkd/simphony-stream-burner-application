import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICommissionServiceTrans } from '../commission-service-trans.model';
import { CommissionServiceTransService } from '../service/commission-service-trans.service';

@Component({
  templateUrl: './commission-service-trans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CommissionServiceTransDeleteDialogComponent {
  commissionServiceTrans?: ICommissionServiceTrans;

  protected commissionServiceTransService = inject(CommissionServiceTransService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commissionServiceTransService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
