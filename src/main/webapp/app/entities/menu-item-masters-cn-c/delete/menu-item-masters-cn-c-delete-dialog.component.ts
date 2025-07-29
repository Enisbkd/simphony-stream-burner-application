import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';
import { MenuItemMastersCnCService } from '../service/menu-item-masters-cn-c.service';

@Component({
  templateUrl: './menu-item-masters-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MenuItemMastersCnCDeleteDialogComponent {
  menuItemMastersCnC?: IMenuItemMastersCnC;

  protected menuItemMastersCnCService = inject(MenuItemMastersCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.menuItemMastersCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
