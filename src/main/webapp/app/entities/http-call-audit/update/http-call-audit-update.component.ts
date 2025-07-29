import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHttpCallAudit } from '../http-call-audit.model';
import { HttpCallAuditService } from '../service/http-call-audit.service';
import { HttpCallAuditFormGroup, HttpCallAuditFormService } from './http-call-audit-form.service';

@Component({
  selector: 'jhi-http-call-audit-update',
  templateUrl: './http-call-audit-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HttpCallAuditUpdateComponent implements OnInit {
  isSaving = false;
  httpCallAudit: IHttpCallAudit | null = null;

  protected httpCallAuditService = inject(HttpCallAuditService);
  protected httpCallAuditFormService = inject(HttpCallAuditFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HttpCallAuditFormGroup = this.httpCallAuditFormService.createHttpCallAuditFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ httpCallAudit }) => {
      this.httpCallAudit = httpCallAudit;
      if (httpCallAudit) {
        this.updateForm(httpCallAudit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const httpCallAudit = this.httpCallAuditFormService.getHttpCallAudit(this.editForm);
    if (httpCallAudit.id !== null) {
      this.subscribeToSaveResponse(this.httpCallAuditService.update(httpCallAudit));
    } else {
      this.subscribeToSaveResponse(this.httpCallAuditService.create(httpCallAudit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHttpCallAudit>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(httpCallAudit: IHttpCallAudit): void {
    this.httpCallAudit = httpCallAudit;
    this.httpCallAuditFormService.resetForm(this.editForm, httpCallAudit);
  }
}
