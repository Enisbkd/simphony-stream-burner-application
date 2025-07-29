import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMajorGroupCnC } from '../major-group-cn-c.model';
import { MajorGroupCnCService } from '../service/major-group-cn-c.service';

@Component({
  templateUrl: './major-group-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MajorGroupCnCDeleteDialogComponent {
  majorGroupCnC?: IMajorGroupCnC;

  protected majorGroupCnCService = inject(MajorGroupCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.majorGroupCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
