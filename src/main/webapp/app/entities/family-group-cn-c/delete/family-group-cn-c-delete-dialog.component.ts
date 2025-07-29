import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFamilyGroupCnC } from '../family-group-cn-c.model';
import { FamilyGroupCnCService } from '../service/family-group-cn-c.service';

@Component({
  templateUrl: './family-group-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FamilyGroupCnCDeleteDialogComponent {
  familyGroupCnC?: IFamilyGroupCnC;

  protected familyGroupCnCService = inject(FamilyGroupCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.familyGroupCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
