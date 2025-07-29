import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGuestCheckBI } from 'app/entities/guest-check-bi/guest-check-bi.model';
import { GuestCheckBIService } from 'app/entities/guest-check-bi/service/guest-check-bi.service';
import { IDetailLineBI } from '../detail-line-bi.model';
import { DetailLineBIService } from '../service/detail-line-bi.service';
import { DetailLineBIFormGroup, DetailLineBIFormService } from './detail-line-bi-form.service';

@Component({
  selector: 'jhi-detail-line-bi-update',
  templateUrl: './detail-line-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DetailLineBIUpdateComponent implements OnInit {
  isSaving = false;
  detailLineBI: IDetailLineBI | null = null;

  guestCheckBISSharedCollection: IGuestCheckBI[] = [];

  protected detailLineBIService = inject(DetailLineBIService);
  protected detailLineBIFormService = inject(DetailLineBIFormService);
  protected guestCheckBIService = inject(GuestCheckBIService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DetailLineBIFormGroup = this.detailLineBIFormService.createDetailLineBIFormGroup();

  compareGuestCheckBI = (o1: IGuestCheckBI | null, o2: IGuestCheckBI | null): boolean =>
    this.guestCheckBIService.compareGuestCheckBI(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailLineBI }) => {
      this.detailLineBI = detailLineBI;
      if (detailLineBI) {
        this.updateForm(detailLineBI);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailLineBI = this.detailLineBIFormService.getDetailLineBI(this.editForm);
    if (detailLineBI.id !== null) {
      this.subscribeToSaveResponse(this.detailLineBIService.update(detailLineBI));
    } else {
      this.subscribeToSaveResponse(this.detailLineBIService.create(detailLineBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailLineBI>>): void {
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

  protected updateForm(detailLineBI: IDetailLineBI): void {
    this.detailLineBI = detailLineBI;
    this.detailLineBIFormService.resetForm(this.editForm, detailLineBI);

    this.guestCheckBISSharedCollection = this.guestCheckBIService.addGuestCheckBIToCollectionIfMissing<IGuestCheckBI>(
      this.guestCheckBISSharedCollection,
      detailLineBI.guestCheckBI,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.guestCheckBIService
      .query()
      .pipe(map((res: HttpResponse<IGuestCheckBI[]>) => res.body ?? []))
      .pipe(
        map((guestCheckBIS: IGuestCheckBI[]) =>
          this.guestCheckBIService.addGuestCheckBIToCollectionIfMissing<IGuestCheckBI>(guestCheckBIS, this.detailLineBI?.guestCheckBI),
        ),
      )
      .subscribe((guestCheckBIS: IGuestCheckBI[]) => (this.guestCheckBISSharedCollection = guestCheckBIS));
  }
}
