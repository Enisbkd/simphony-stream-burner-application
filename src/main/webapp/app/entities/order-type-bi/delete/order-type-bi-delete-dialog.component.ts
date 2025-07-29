import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrderTypeBI } from '../order-type-bi.model';
import { OrderTypeBIService } from '../service/order-type-bi.service';

@Component({
  templateUrl: './order-type-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrderTypeBIDeleteDialogComponent {
  orderTypeBI?: IOrderTypeBI;

  protected orderTypeBIService = inject(OrderTypeBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTypeBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
