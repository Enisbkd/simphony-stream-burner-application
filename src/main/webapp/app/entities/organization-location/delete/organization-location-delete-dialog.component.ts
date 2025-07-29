import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrganizationLocation } from '../organization-location.model';
import { OrganizationLocationService } from '../service/organization-location.service';

@Component({
  templateUrl: './organization-location-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrganizationLocationDeleteDialogComponent {
  organizationLocation?: IOrganizationLocation;

  protected organizationLocationService = inject(OrganizationLocationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationLocationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
