import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFamilyGroupCnC } from '../family-group-cn-c.model';

@Component({
  selector: 'jhi-family-group-cn-c-detail',
  templateUrl: './family-group-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FamilyGroupCnCDetailComponent {
  familyGroupCnC = input<IFamilyGroupCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
