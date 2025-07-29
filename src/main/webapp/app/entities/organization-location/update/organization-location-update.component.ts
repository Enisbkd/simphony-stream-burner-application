import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrganizationLocation } from '../organization-location.model';
import { OrganizationLocationService } from '../service/organization-location.service';
import { OrganizationLocationFormGroup, OrganizationLocationFormService } from './organization-location-form.service';

@Component({
  selector: 'jhi-organization-location-update',
  templateUrl: './organization-location-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrganizationLocationUpdateComponent implements OnInit {
  isSaving = false;
  organizationLocation: IOrganizationLocation | null = null;

  protected organizationLocationService = inject(OrganizationLocationService);
  protected organizationLocationFormService = inject(OrganizationLocationFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrganizationLocationFormGroup = this.organizationLocationFormService.createOrganizationLocationFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationLocation }) => {
      this.organizationLocation = organizationLocation;
      if (organizationLocation) {
        this.updateForm(organizationLocation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationLocation = this.organizationLocationFormService.getOrganizationLocation(this.editForm);
    if (organizationLocation.id !== null) {
      this.subscribeToSaveResponse(this.organizationLocationService.update(organizationLocation));
    } else {
      this.subscribeToSaveResponse(this.organizationLocationService.create(organizationLocation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationLocation>>): void {
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

  protected updateForm(organizationLocation: IOrganizationLocation): void {
    this.organizationLocation = organizationLocation;
    this.organizationLocationFormService.resetForm(this.editForm, organizationLocation);
  }
}
