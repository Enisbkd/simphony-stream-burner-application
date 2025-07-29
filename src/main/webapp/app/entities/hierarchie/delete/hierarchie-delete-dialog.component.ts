import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHierarchie } from '../hierarchie.model';
import { HierarchieService } from '../service/hierarchie.service';

@Component({
  templateUrl: './hierarchie-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HierarchieDeleteDialogComponent {
  hierarchie?: IHierarchie;

  protected hierarchieService = inject(HierarchieService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hierarchieService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
