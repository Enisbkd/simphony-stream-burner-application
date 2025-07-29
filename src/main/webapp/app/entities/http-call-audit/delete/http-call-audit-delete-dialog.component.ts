import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHttpCallAudit } from '../http-call-audit.model';
import { HttpCallAuditService } from '../service/http-call-audit.service';

@Component({
  templateUrl: './http-call-audit-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HttpCallAuditDeleteDialogComponent {
  httpCallAudit?: IHttpCallAudit;

  protected httpCallAuditService = inject(HttpCallAuditService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.httpCallAuditService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
