import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICommissionServiceBI } from '../commission-service-bi.model';
import { CommissionServiceBIService } from '../service/commission-service-bi.service';
import { CommissionServiceBIFormGroup, CommissionServiceBIFormService } from './commission-service-bi-form.service';

@Component({
  selector: 'jhi-commission-service-bi-update',
  templateUrl: './commission-service-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CommissionServiceBIUpdateComponent implements OnInit {
  isSaving = false;
  commissionServiceBI: ICommissionServiceBI | null = null;

  protected commissionServiceBIService = inject(CommissionServiceBIService);
  protected commissionServiceBIFormService = inject(CommissionServiceBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CommissionServiceBIFormGroup = this.commissionServiceBIFormService.createCommissionServiceBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commissionServiceBI }) => {
      this.commissionServiceBI = commissionServiceBI;
      if (commissionServiceBI) {
        this.updateForm(commissionServiceBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commissionServiceBI = this.commissionServiceBIFormService.getCommissionServiceBI(this.editForm);
    if (commissionServiceBI.id !== null) {
      this.subscribeToSaveResponse(this.commissionServiceBIService.update(commissionServiceBI));
    } else {
      this.subscribeToSaveResponse(this.commissionServiceBIService.create(commissionServiceBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommissionServiceBI>>): void {
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

  protected updateForm(commissionServiceBI: ICommissionServiceBI): void {
    this.commissionServiceBI = commissionServiceBI;
    this.commissionServiceBIFormService.resetForm(this.editForm, commissionServiceBI);
  }
}
