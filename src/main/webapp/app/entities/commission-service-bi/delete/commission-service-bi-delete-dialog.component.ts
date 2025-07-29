import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICommissionServiceBI } from '../commission-service-bi.model';
import { CommissionServiceBIService } from '../service/commission-service-bi.service';

@Component({
  templateUrl: './commission-service-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CommissionServiceBIDeleteDialogComponent {
  commissionServiceBI?: ICommissionServiceBI;

  protected commissionServiceBIService = inject(CommissionServiceBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commissionServiceBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
