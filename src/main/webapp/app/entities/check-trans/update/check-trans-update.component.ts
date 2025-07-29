import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICheckTrans } from '../check-trans.model';
import { CheckTransService } from '../service/check-trans.service';
import { CheckTransFormGroup, CheckTransFormService } from './check-trans-form.service';

@Component({
  selector: 'jhi-check-trans-update',
  templateUrl: './check-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CheckTransUpdateComponent implements OnInit {
  isSaving = false;
  checkTrans: ICheckTrans | null = null;

  protected checkTransService = inject(CheckTransService);
  protected checkTransFormService = inject(CheckTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CheckTransFormGroup = this.checkTransFormService.createCheckTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkTrans }) => {
      this.checkTrans = checkTrans;
      if (checkTrans) {
        this.updateForm(checkTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkTrans = this.checkTransFormService.getCheckTrans(this.editForm);
    if (checkTrans.id !== null) {
      this.subscribeToSaveResponse(this.checkTransService.update(checkTrans));
    } else {
      this.subscribeToSaveResponse(this.checkTransService.create(checkTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckTrans>>): void {
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

  protected updateForm(checkTrans: ICheckTrans): void {
    this.checkTrans = checkTrans;
    this.checkTransFormService.resetForm(this.editForm, checkTrans);
  }
}
