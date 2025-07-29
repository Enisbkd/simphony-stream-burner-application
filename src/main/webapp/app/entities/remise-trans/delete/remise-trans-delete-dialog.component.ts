import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRemiseTrans } from '../remise-trans.model';
import { RemiseTransService } from '../service/remise-trans.service';

@Component({
  templateUrl: './remise-trans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RemiseTransDeleteDialogComponent {
  remiseTrans?: IRemiseTrans;

  protected remiseTransService = inject(RemiseTransService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.remiseTransService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
