import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IHttpCallAudit } from '../http-call-audit.model';

@Component({
  selector: 'jhi-http-call-audit-detail',
  templateUrl: './http-call-audit-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class HttpCallAuditDetailComponent {
  httpCallAudit = input<IHttpCallAudit | null>(null);

  previousState(): void {
    window.history.back();
  }
}
