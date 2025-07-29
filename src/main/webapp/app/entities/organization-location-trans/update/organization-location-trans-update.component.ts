import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrganizationLocationTrans } from '../organization-location-trans.model';
import { OrganizationLocationTransService } from '../service/organization-location-trans.service';
import { OrganizationLocationTransFormGroup, OrganizationLocationTransFormService } from './organization-location-trans-form.service';

@Component({
  selector: 'jhi-organization-location-trans-update',
  templateUrl: './organization-location-trans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrganizationLocationTransUpdateComponent implements OnInit {
  isSaving = false;
  organizationLocationTrans: IOrganizationLocationTrans | null = null;

  protected organizationLocationTransService = inject(OrganizationLocationTransService);
  protected organizationLocationTransFormService = inject(OrganizationLocationTransFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrganizationLocationTransFormGroup = this.organizationLocationTransFormService.createOrganizationLocationTransFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationLocationTrans }) => {
      this.organizationLocationTrans = organizationLocationTrans;
      if (organizationLocationTrans) {
        this.updateForm(organizationLocationTrans);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationLocationTrans = this.organizationLocationTransFormService.getOrganizationLocationTrans(this.editForm);
    if (organizationLocationTrans.id !== null) {
      this.subscribeToSaveResponse(this.organizationLocationTransService.update(organizationLocationTrans));
    } else {
      this.subscribeToSaveResponse(this.organizationLocationTransService.create(organizationLocationTrans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationLocationTrans>>): void {
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

  protected updateForm(organizationLocationTrans: IOrganizationLocationTrans): void {
    this.organizationLocationTrans = organizationLocationTrans;
    this.organizationLocationTransFormService.resetForm(this.editForm, organizationLocationTrans);
  }
}
