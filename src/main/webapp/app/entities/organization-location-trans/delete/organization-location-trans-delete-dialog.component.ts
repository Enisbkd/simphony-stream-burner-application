import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrganizationLocationTrans } from '../organization-location-trans.model';
import { OrganizationLocationTransService } from '../service/organization-location-trans.service';

@Component({
  templateUrl: './organization-location-trans-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrganizationLocationTransDeleteDialogComponent {
  organizationLocationTrans?: IOrganizationLocationTrans;

  protected organizationLocationTransService = inject(OrganizationLocationTransService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationLocationTransService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
