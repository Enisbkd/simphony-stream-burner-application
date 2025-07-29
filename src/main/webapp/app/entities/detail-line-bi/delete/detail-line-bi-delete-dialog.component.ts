import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDetailLineBI } from '../detail-line-bi.model';
import { DetailLineBIService } from '../service/detail-line-bi.service';

@Component({
  templateUrl: './detail-line-bi-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DetailLineBIDeleteDialogComponent {
  detailLineBI?: IDetailLineBI;

  protected detailLineBIService = inject(DetailLineBIService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailLineBIService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
