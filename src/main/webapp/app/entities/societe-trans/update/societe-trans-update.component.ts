import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISocieteTrans } from '../societe-trans.model';
import { SocieteTransService } from '../service/societe-trans.service';
import { SocieteTransFormGroup, SocieteTransFormService } from './societe-trans-form.service';

@Component({
  selector: 'jhi-societe-trans-update',
  templateUrl: './societe-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SocieteTransUpdateComponent implements OnInit {
  isSaving = false;
  societeTrans: ISocieteTrans | null = null;

  protected societeTransService = inject(SocieteTransService);
  protected societeTransFormService = inject(SocieteTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SocieteTransFormGroup = this.societeTransFormService.createSocieteTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societeTrans }) => {
      this.societeTrans = societeTrans;
      if (societeTrans) {
        this.updateForm(societeTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societeTrans = this.societeTransFormService.getSocieteTrans(this.editForm);
    if (societeTrans.id !== null) {
      this.subscribeToSaveResponse(this.societeTransService.update(societeTrans));
    } else {
      this.subscribeToSaveResponse(this.societeTransService.create(societeTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocieteTrans>>): void {
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

  protected updateForm(societeTrans: ISocieteTrans): void {
    this.societeTrans = societeTrans;
    this.societeTransFormService.resetForm(this.editForm, societeTrans);
  }
}
