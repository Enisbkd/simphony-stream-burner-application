import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { PointDeVenteCnCService } from '../service/point-de-vente-cn-c.service';
import { PointDeVenteCnCFormGroup, PointDeVenteCnCFormService } from './point-de-vente-cn-c-form.service';

@Component({
  selector: 'jhi-point-de-vente-cn-c-update',
  templateUrl: './point-de-vente-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PointDeVenteCnCUpdateComponent implements OnInit {
  isSaving = false;
  pointDeVenteCnC: IPointDeVenteCnC | null = null;

  protected pointDeVenteCnCService = inject(PointDeVenteCnCService);
  protected pointDeVenteCnCFormService = inject(PointDeVenteCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PointDeVenteCnCFormGroup = this.pointDeVenteCnCFormService.createPointDeVenteCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointDeVenteCnC }) => {
      this.pointDeVenteCnC = pointDeVenteCnC;
      if (pointDeVenteCnC) {
        this.updateForm(pointDeVenteCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointDeVenteCnC = this.pointDeVenteCnCFormService.getPointDeVenteCnC(this.editForm);
    if (pointDeVenteCnC.id !== null) {
      this.subscribeToSaveResponse(this.pointDeVenteCnCService.update(pointDeVenteCnC));
    } else {
      this.subscribeToSaveResponse(this.pointDeVenteCnCService.create(pointDeVenteCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointDeVenteCnC>>): void {
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

  protected updateForm(pointDeVenteCnC: IPointDeVenteCnC): void {
    this.pointDeVenteCnC = pointDeVenteCnC;
    this.pointDeVenteCnCFormService.resetForm(this.editForm, pointDeVenteCnC);
  }
}
