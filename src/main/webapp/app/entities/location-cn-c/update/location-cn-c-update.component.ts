import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILocationCnC } from '../location-cn-c.model';
import { LocationCnCService } from '../service/location-cn-c.service';
import { LocationCnCFormGroup, LocationCnCFormService } from './location-cn-c-form.service';

@Component({
  selector: 'jhi-location-cn-c-update',
  templateUrl: './location-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LocationCnCUpdateComponent implements OnInit {
  isSaving = false;
  locationCnC: ILocationCnC | null = null;

  protected locationCnCService = inject(LocationCnCService);
  protected locationCnCFormService = inject(LocationCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: LocationCnCFormGroup = this.locationCnCFormService.createLocationCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locationCnC }) => {
      this.locationCnC = locationCnC;
      if (locationCnC) {
        this.updateForm(locationCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const locationCnC = this.locationCnCFormService.getLocationCnC(this.editForm);
    if (locationCnC.id !== null) {
      this.subscribeToSaveResponse(this.locationCnCService.update(locationCnC));
    } else {
      this.subscribeToSaveResponse(this.locationCnCService.create(locationCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationCnC>>): void {
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

  protected updateForm(locationCnC: ILocationCnC): void {
    this.locationCnC = locationCnC;
    this.locationCnCFormService.resetForm(this.editForm, locationCnC);
  }
}
