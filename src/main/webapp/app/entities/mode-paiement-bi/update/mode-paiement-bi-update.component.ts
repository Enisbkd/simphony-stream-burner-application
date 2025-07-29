import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IModePaiementBI } from '../mode-paiement-bi.model';
import { ModePaiementBIService } from '../service/mode-paiement-bi.service';
import { ModePaiementBIFormGroup, ModePaiementBIFormService } from './mode-paiement-bi-form.service';

@Component({
  selector: 'jhi-mode-paiement-bi-update',
  templateUrl: './mode-paiement-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ModePaiementBIUpdateComponent implements OnInit {
  isSaving = false;
  modePaiementBI: IModePaiementBI | null = null;

  protected modePaiementBIService = inject(ModePaiementBIService);
  protected modePaiementBIFormService = inject(ModePaiementBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ModePaiementBIFormGroup = this.modePaiementBIFormService.createModePaiementBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modePaiementBI }) => {
      this.modePaiementBI = modePaiementBI;
      if (modePaiementBI) {
        this.updateForm(modePaiementBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modePaiementBI = this.modePaiementBIFormService.getModePaiementBI(this.editForm);
    if (modePaiementBI.id !== null) {
      this.subscribeToSaveResponse(this.modePaiementBIService.update(modePaiementBI));
    } else {
      this.subscribeToSaveResponse(this.modePaiementBIService.create(modePaiementBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModePaiementBI>>): void {
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

  protected updateForm(modePaiementBI: IModePaiementBI): void {
    this.modePaiementBI = modePaiementBI;
    this.modePaiementBIFormService.resetForm(this.editForm, modePaiementBI);
  }
}
