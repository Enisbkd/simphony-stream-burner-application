import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGuestCheckBI } from '../guest-check-bi.model';
import { GuestCheckBIService } from '../service/guest-check-bi.service';
import { GuestCheckBIFormGroup, GuestCheckBIFormService } from './guest-check-bi-form.service';

@Component({
  selector: 'jhi-guest-check-bi-update',
  templateUrl: './guest-check-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GuestCheckBIUpdateComponent implements OnInit {
  isSaving = false;
  guestCheckBI: IGuestCheckBI | null = null;

  protected guestCheckBIService = inject(GuestCheckBIService);
  protected guestCheckBIFormService = inject(GuestCheckBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GuestCheckBIFormGroup = this.guestCheckBIFormService.createGuestCheckBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guestCheckBI }) => {
      this.guestCheckBI = guestCheckBI;
      if (guestCheckBI) {
        this.updateForm(guestCheckBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guestCheckBI = this.guestCheckBIFormService.getGuestCheckBI(this.editForm);
    if (guestCheckBI.id !== null) {
      this.subscribeToSaveResponse(this.guestCheckBIService.update(guestCheckBI));
    } else {
      this.subscribeToSaveResponse(this.guestCheckBIService.create(guestCheckBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuestCheckBI>>): void {
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

  protected updateForm(guestCheckBI: IGuestCheckBI): void {
    this.guestCheckBI = guestCheckBI;
    this.guestCheckBIFormService.resetForm(this.editForm, guestCheckBI);
  }
}
