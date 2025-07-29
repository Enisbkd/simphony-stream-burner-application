import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILocationCnC } from '../location-cn-c.model';
import { LocationCnCService } from '../service/location-cn-c.service';

@Component({
  templateUrl: './location-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LocationCnCDeleteDialogComponent {
  locationCnC?: ILocationCnC;

  protected locationCnCService = inject(LocationCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.locationCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
