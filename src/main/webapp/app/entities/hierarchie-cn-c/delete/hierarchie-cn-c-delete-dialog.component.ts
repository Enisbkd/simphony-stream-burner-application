import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHierarchieCnC } from '../hierarchie-cn-c.model';
import { HierarchieCnCService } from '../service/hierarchie-cn-c.service';

@Component({
  templateUrl: './hierarchie-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HierarchieCnCDeleteDialogComponent {
  hierarchieCnC?: IHierarchieCnC;

  protected hierarchieCnCService = inject(HierarchieCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hierarchieCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
