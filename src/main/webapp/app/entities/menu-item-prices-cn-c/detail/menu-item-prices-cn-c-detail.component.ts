import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';

@Component({
  selector: 'jhi-menu-item-prices-cn-c-detail',
  templateUrl: './menu-item-prices-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MenuItemPricesCnCDetailComponent {
  menuItemPricesCnC = input<IMenuItemPricesCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
