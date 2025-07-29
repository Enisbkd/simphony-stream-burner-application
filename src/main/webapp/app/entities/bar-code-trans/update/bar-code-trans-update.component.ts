import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBarCodeTrans } from '../bar-code-trans.model';
import { BarCodeTransService } from '../service/bar-code-trans.service';
import { BarCodeTransFormGroup, BarCodeTransFormService } from './bar-code-trans-form.service';

@Component({
  selector: 'jhi-bar-code-trans-update',
  templateUrl: './bar-code-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BarCodeTransUpdateComponent implements OnInit {
  isSaving = false;
  barCodeTrans: IBarCodeTrans | null = null;

  protected barCodeTransService = inject(BarCodeTransService);
  protected barCodeTransFormService = inject(BarCodeTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BarCodeTransFormGroup = this.barCodeTransFormService.createBarCodeTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ barCodeTrans }) => {
      this.barCodeTrans = barCodeTrans;
      if (barCodeTrans) {
        this.updateForm(barCodeTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const barCodeTrans = this.barCodeTransFormService.getBarCodeTrans(this.editForm);
    if (barCodeTrans.id !== null) {
      this.subscribeToSaveResponse(this.barCodeTransService.update(barCodeTrans));
    } else {
      this.subscribeToSaveResponse(this.barCodeTransService.create(barCodeTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarCodeTrans>>): void {
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

  protected updateForm(barCodeTrans: IBarCodeTrans): void {
    this.barCodeTrans = barCodeTrans;
    this.barCodeTransFormService.resetForm(this.editForm, barCodeTrans);
  }
}
