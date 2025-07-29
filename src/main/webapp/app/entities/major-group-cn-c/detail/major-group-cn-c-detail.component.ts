import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMajorGroupCnC } from '../major-group-cn-c.model';

@Component({
  selector: 'jhi-major-group-cn-c-detail',
  templateUrl: './major-group-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MajorGroupCnCDetailComponent {
  majorGroupCnC = input<IMajorGroupCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
