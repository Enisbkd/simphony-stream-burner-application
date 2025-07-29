import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITaxeRateTrans } from '../taxe-rate-trans.model';
import { TaxeRateTransService } from '../service/taxe-rate-trans.service';
import { TaxeRateTransFormGroup, TaxeRateTransFormService } from './taxe-rate-trans-form.service';

@Component({
  selector: 'jhi-taxe-rate-trans-update',
  templateUrl: './taxe-rate-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TaxeRateTransUpdateComponent implements OnInit {
  isSaving = false;
  taxeRateTrans: ITaxeRateTrans | null = null;

  protected taxeRateTransService = inject(TaxeRateTransService);
  protected taxeRateTransFormService = inject(TaxeRateTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TaxeRateTransFormGroup = this.taxeRateTransFormService.createTaxeRateTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxeRateTrans }) => {
      this.taxeRateTrans = taxeRateTrans;
      if (taxeRateTrans) {
        this.updateForm(taxeRateTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxeRateTrans = this.taxeRateTransFormService.getTaxeRateTrans(this.editForm);
    if (taxeRateTrans.id !== null) {
      this.subscribeToSaveResponse(this.taxeRateTransService.update(taxeRateTrans));
    } else {
      this.subscribeToSaveResponse(this.taxeRateTransService.create(taxeRateTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxeRateTrans>>): void {
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

  protected updateForm(taxeRateTrans: ITaxeRateTrans): void {
    this.taxeRateTrans = taxeRateTrans;
    this.taxeRateTransFormService.resetForm(this.editForm, taxeRateTrans);
  }
}
