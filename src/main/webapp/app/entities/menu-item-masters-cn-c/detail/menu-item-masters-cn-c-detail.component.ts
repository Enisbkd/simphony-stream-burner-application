import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';

@Component({
  selector: 'jhi-menu-item-masters-cn-c-detail',
  templateUrl: './menu-item-masters-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MenuItemMastersCnCDetailComponent {
  menuItemMastersCnC = input<IMenuItemMastersCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
