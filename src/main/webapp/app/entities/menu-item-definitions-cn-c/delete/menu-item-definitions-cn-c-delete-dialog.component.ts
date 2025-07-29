import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';
import { MenuItemDefinitionsCnCService } from '../service/menu-item-definitions-cn-c.service';

@Component({
  templateUrl: './menu-item-definitions-cn-c-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MenuItemDefinitionsCnCDeleteDialogComponent {
  menuItemDefinitionsCnC?: IMenuItemDefinitionsCnC;

  protected menuItemDefinitionsCnCService = inject(MenuItemDefinitionsCnCService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.menuItemDefinitionsCnCService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
