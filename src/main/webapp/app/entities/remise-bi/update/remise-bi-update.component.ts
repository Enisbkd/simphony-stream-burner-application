import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRemiseBI } from '../remise-bi.model';
import { RemiseBIService } from '../service/remise-bi.service';
import { RemiseBIFormGroup, RemiseBIFormService } from './remise-bi-form.service';

@Component({
  selector: 'jhi-remise-bi-update',
  templateUrl: './remise-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RemiseBIUpdateComponent implements OnInit {
  isSaving = false;
  remiseBI: IRemiseBI | null = null;

  protected remiseBIService = inject(RemiseBIService);
  protected remiseBIFormService = inject(RemiseBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RemiseBIFormGroup = this.remiseBIFormService.createRemiseBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remiseBI }) => {
      this.remiseBI = remiseBI;
      if (remiseBI) {
        this.updateForm(remiseBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const remiseBI = this.remiseBIFormService.getRemiseBI(this.editForm);
    if (remiseBI.id !== null) {
      this.subscribeToSaveResponse(this.remiseBIService.update(remiseBI));
    } else {
      this.subscribeToSaveResponse(this.remiseBIService.create(remiseBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemiseBI>>): void {
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

  protected updateForm(remiseBI: IRemiseBI): void {
    this.remiseBI = remiseBI;
    this.remiseBIFormService.resetForm(this.editForm, remiseBI);
  }
}
