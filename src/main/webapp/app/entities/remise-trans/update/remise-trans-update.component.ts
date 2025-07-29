import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRemiseTrans } from '../remise-trans.model';
import { RemiseTransService } from '../service/remise-trans.service';
import { RemiseTransFormGroup, RemiseTransFormService } from './remise-trans-form.service';

@Component({
  selector: 'jhi-remise-trans-update',
  templateUrl: './remise-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RemiseTransUpdateComponent implements OnInit {
  isSaving = false;
  remiseTrans: IRemiseTrans | null = null;

  protected remiseTransService = inject(RemiseTransService);
  protected remiseTransFormService = inject(RemiseTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RemiseTransFormGroup = this.remiseTransFormService.createRemiseTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remiseTrans }) => {
      this.remiseTrans = remiseTrans;
      if (remiseTrans) {
        this.updateForm(remiseTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const remiseTrans = this.remiseTransFormService.getRemiseTrans(this.editForm);
    if (remiseTrans.id !== null) {
      this.subscribeToSaveResponse(this.remiseTransService.update(remiseTrans));
    } else {
      this.subscribeToSaveResponse(this.remiseTransService.create(remiseTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemiseTrans>>): void {
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

  protected updateForm(remiseTrans: IRemiseTrans): void {
    this.remiseTrans = remiseTrans;
    this.remiseTransFormService.resetForm(this.editForm, remiseTrans);
  }
}
