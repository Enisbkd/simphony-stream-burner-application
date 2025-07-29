import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IModePaiementTrans } from '../mode-paiement-trans.model';
import { ModePaiementTransService } from '../service/mode-paiement-trans.service';
import { ModePaiementTransFormGroup, ModePaiementTransFormService } from './mode-paiement-trans-form.service';

@Component({
  selector: 'jhi-mode-paiement-trans-update',
  templateUrl: './mode-paiement-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ModePaiementTransUpdateComponent implements OnInit {
  isSaving = false;
  modePaiementTrans: IModePaiementTrans | null = null;

  protected modePaiementTransService = inject(ModePaiementTransService);
  protected modePaiementTransFormService = inject(ModePaiementTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ModePaiementTransFormGroup = this.modePaiementTransFormService.createModePaiementTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modePaiementTrans }) => {
      this.modePaiementTrans = modePaiementTrans;
      if (modePaiementTrans) {
        this.updateForm(modePaiementTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modePaiementTrans = this.modePaiementTransFormService.getModePaiementTrans(this.editForm);
    if (modePaiementTrans.id !== null) {
      this.subscribeToSaveResponse(this.modePaiementTransService.update(modePaiementTrans));
    } else {
      this.subscribeToSaveResponse(this.modePaiementTransService.create(modePaiementTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModePaiementTrans>>): void {
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

  protected updateForm(modePaiementTrans: IModePaiementTrans): void {
    this.modePaiementTrans = modePaiementTrans;
    this.modePaiementTransFormService.resetForm(this.editForm, modePaiementTrans);
  }
}
