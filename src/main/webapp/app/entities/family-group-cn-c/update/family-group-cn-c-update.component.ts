import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFamilyGroupCnC } from '../family-group-cn-c.model';
import { FamilyGroupCnCService } from '../service/family-group-cn-c.service';
import { FamilyGroupCnCFormGroup, FamilyGroupCnCFormService } from './family-group-cn-c-form.service';

@Component({
  selector: 'jhi-family-group-cn-c-update',
  templateUrl: './family-group-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FamilyGroupCnCUpdateComponent implements OnInit {
  isSaving = false;
  familyGroupCnC: IFamilyGroupCnC | null = null;

  protected familyGroupCnCService = inject(FamilyGroupCnCService);
  protected familyGroupCnCFormService = inject(FamilyGroupCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FamilyGroupCnCFormGroup = this.familyGroupCnCFormService.createFamilyGroupCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ familyGroupCnC }) => {
      this.familyGroupCnC = familyGroupCnC;
      if (familyGroupCnC) {
        this.updateForm(familyGroupCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const familyGroupCnC = this.familyGroupCnCFormService.getFamilyGroupCnC(this.editForm);
    if (familyGroupCnC.id !== null) {
      this.subscribeToSaveResponse(this.familyGroupCnCService.update(familyGroupCnC));
    } else {
      this.subscribeToSaveResponse(this.familyGroupCnCService.create(familyGroupCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyGroupCnC>>): void {
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

  protected updateForm(familyGroupCnC: IFamilyGroupCnC): void {
    this.familyGroupCnC = familyGroupCnC;
    this.familyGroupCnCFormService.resetForm(this.editForm, familyGroupCnC);
  }
}
