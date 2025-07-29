import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICommissionServiceTrans } from '../commission-service-trans.model';
import { CommissionServiceTransService } from '../service/commission-service-trans.service';
import { CommissionServiceTransFormGroup, CommissionServiceTransFormService } from './commission-service-trans-form.service';

@Component({
  selector: 'jhi-commission-service-trans-update',
  templateUrl: './commission-service-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CommissionServiceTransUpdateComponent implements OnInit {
  isSaving = false;
  commissionServiceTrans: ICommissionServiceTrans | null = null;

  protected commissionServiceTransService = inject(CommissionServiceTransService);
  protected commissionServiceTransFormService = inject(CommissionServiceTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CommissionServiceTransFormGroup = this.commissionServiceTransFormService.createCommissionServiceTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commissionServiceTrans }) => {
      this.commissionServiceTrans = commissionServiceTrans;
      if (commissionServiceTrans) {
        this.updateForm(commissionServiceTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commissionServiceTrans = this.commissionServiceTransFormService.getCommissionServiceTrans(this.editForm);
    if (commissionServiceTrans.id !== null) {
      this.subscribeToSaveResponse(this.commissionServiceTransService.update(commissionServiceTrans));
    } else {
      this.subscribeToSaveResponse(this.commissionServiceTransService.create(commissionServiceTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommissionServiceTrans>>): void {
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

  protected updateForm(commissionServiceTrans: ICommissionServiceTrans): void {
    this.commissionServiceTrans = commissionServiceTrans;
    this.commissionServiceTransFormService.resetForm(this.editForm, commissionServiceTrans);
  }
}
