import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPointDeVenteTrans } from '../point-de-vente-trans.model';
import { PointDeVenteTransService } from '../service/point-de-vente-trans.service';
import { PointDeVenteTransFormGroup, PointDeVenteTransFormService } from './point-de-vente-trans-form.service';

@Component({
  selector: 'jhi-point-de-vente-trans-update',
  templateUrl: './point-de-vente-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PointDeVenteTransUpdateComponent implements OnInit {
  isSaving = false;
  pointDeVenteTrans: IPointDeVenteTrans | null = null;

  protected pointDeVenteTransService = inject(PointDeVenteTransService);
  protected pointDeVenteTransFormService = inject(PointDeVenteTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PointDeVenteTransFormGroup = this.pointDeVenteTransFormService.createPointDeVenteTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointDeVenteTrans }) => {
      this.pointDeVenteTrans = pointDeVenteTrans;
      if (pointDeVenteTrans) {
        this.updateForm(pointDeVenteTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointDeVenteTrans = this.pointDeVenteTransFormService.getPointDeVenteTrans(this.editForm);
    if (pointDeVenteTrans.id !== null) {
      this.subscribeToSaveResponse(this.pointDeVenteTransService.update(pointDeVenteTrans));
    } else {
      this.subscribeToSaveResponse(this.pointDeVenteTransService.create(pointDeVenteTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointDeVenteTrans>>): void {
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

  protected updateForm(pointDeVenteTrans: IPointDeVenteTrans): void {
    this.pointDeVenteTrans = pointDeVenteTrans;
    this.pointDeVenteTransFormService.resetForm(this.editForm, pointDeVenteTrans);
  }
}
