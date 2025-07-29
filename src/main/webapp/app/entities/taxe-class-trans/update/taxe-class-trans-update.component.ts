import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITaxeClassTrans } from '../taxe-class-trans.model';
import { TaxeClassTransService } from '../service/taxe-class-trans.service';
import { TaxeClassTransFormGroup, TaxeClassTransFormService } from './taxe-class-trans-form.service';

@Component({
  selector: 'jhi-taxe-class-trans-update',
  templateUrl: './taxe-class-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TaxeClassTransUpdateComponent implements OnInit {
  isSaving = false;
  taxeClassTrans: ITaxeClassTrans | null = null;

  protected taxeClassTransService = inject(TaxeClassTransService);
  protected taxeClassTransFormService = inject(TaxeClassTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TaxeClassTransFormGroup = this.taxeClassTransFormService.createTaxeClassTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxeClassTrans }) => {
      this.taxeClassTrans = taxeClassTrans;
      if (taxeClassTrans) {
        this.updateForm(taxeClassTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxeClassTrans = this.taxeClassTransFormService.getTaxeClassTrans(this.editForm);
    if (taxeClassTrans.id !== null) {
      this.subscribeToSaveResponse(this.taxeClassTransService.update(taxeClassTrans));
    } else {
      this.subscribeToSaveResponse(this.taxeClassTransService.create(taxeClassTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxeClassTrans>>): void {
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

  protected updateForm(taxeClassTrans: ITaxeClassTrans): void {
    this.taxeClassTrans = taxeClassTrans;
    this.taxeClassTransFormService.resetForm(this.editForm, taxeClassTrans);
  }
}
