import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';

@Component({
  selector: 'jhi-menu-item-definitions-cn-c-detail',
  templateUrl: './menu-item-definitions-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MenuItemDefinitionsCnCDetailComponent {
  menuItemDefinitionsCnC = input<IMenuItemDefinitionsCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
