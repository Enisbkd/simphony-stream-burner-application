import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEmployeeCnC } from '../employee-cn-c.model';

@Component({
  selector: 'jhi-employee-cn-c-detail',
  templateUrl: './employee-cn-c-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class EmployeeCnCDetailComponent {
  employeeCnC = input<IEmployeeCnC | null>(null);

  previousState(): void {
    window.history.back();
  }
}
