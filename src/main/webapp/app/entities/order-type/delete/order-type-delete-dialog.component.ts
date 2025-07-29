import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrderType } from '../order-type.model';
import { OrderTypeService } from '../service/order-type.service';

@Component({
  templateUrl: './order-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrderTypeDeleteDialogComponent {
  orderType?: IOrderType;

  protected orderTypeService = inject(OrderTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
