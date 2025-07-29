import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { PointDeVenteCnCService } from '../service/point-de-vente-cn-c.service';

@Component({
  templateUrl: './point-de-vente-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PointDeVenteCnCDeleteDialogComponent {
  pointDeVenteCnC?: IPointDeVenteCnC;

  protected pointDeVenteCnCService = inject(PointDeVenteCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pointDeVenteCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
