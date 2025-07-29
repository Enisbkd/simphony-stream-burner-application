import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrderChannel } from '../order-channel.model';
import { OrderChannelService } from '../service/order-channel.service';

@Component({
  templateUrl: './order-channel-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrderChannelDeleteDialogComponent {
  orderChannel?: IOrderChannel;

  protected orderChannelService = inject(OrderChannelService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderChannelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
