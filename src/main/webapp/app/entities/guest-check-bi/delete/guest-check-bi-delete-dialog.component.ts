import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGuestCheckBI } from '../guest-check-bi.model';
import { GuestCheckBIService } from '../service/guest-check-bi.service';

@Component({
  templateUrl: './guest-check-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GuestCheckBIDeleteDialogComponent {
  guestCheckBI?: IGuestCheckBI;

  protected guestCheckBIService = inject(GuestCheckBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.guestCheckBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
