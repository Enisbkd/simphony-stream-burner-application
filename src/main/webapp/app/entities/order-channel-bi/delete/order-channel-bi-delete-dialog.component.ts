import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrderChannelBI } from '../order-channel-bi.model';
import { OrderChannelBIService } from '../service/order-channel-bi.service';

@Component({
  templateUrl: './order-channel-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrderChannelBIDeleteDialogComponent {
  orderChannelBI?: IOrderChannelBI;

  protected orderChannelBIService = inject(OrderChannelBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderChannelBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
