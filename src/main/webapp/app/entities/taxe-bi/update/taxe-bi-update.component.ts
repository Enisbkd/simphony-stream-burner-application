import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITaxeBI } from '../taxe-bi.model';
import { TaxeBIService } from '../service/taxe-bi.service';
import { TaxeBIFormGroup, TaxeBIFormService } from './taxe-bi-form.service';

@Component({
  selector: 'jhi-taxe-bi-update',
  templateUrl: './taxe-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TaxeBIUpdateComponent implements OnInit {
  isSaving = false;
  taxeBI: ITaxeBI | null = null;

  protected taxeBIService = inject(TaxeBIService);
  protected taxeBIFormService = inject(TaxeBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TaxeBIFormGroup = this.taxeBIFormService.createTaxeBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxeBI }) => {
      this.taxeBI = taxeBI;
      if (taxeBI) {
        this.updateForm(taxeBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxeBI = this.taxeBIFormService.getTaxeBI(this.editForm);
    if (taxeBI.id !== null) {
      this.subscribeToSaveResponse(this.taxeBIService.update(taxeBI));
    } else {
      this.subscribeToSaveResponse(this.taxeBIService.create(taxeBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxeBI>>): void {
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

  protected updateForm(taxeBI: ITaxeBI): void {
    this.taxeBI = taxeBI;
    this.taxeBIFormService.resetForm(this.editForm, taxeBI);
  }
}
