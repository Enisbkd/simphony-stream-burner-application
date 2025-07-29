import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMajorGroupCnC } from '../major-group-cn-c.model';
import { MajorGroupCnCService } from '../service/major-group-cn-c.service';
import { MajorGroupCnCFormGroup, MajorGroupCnCFormService } from './major-group-cn-c-form.service';

@Component({
  selector: 'jhi-major-group-cn-c-update',
  templateUrl: './major-group-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MajorGroupCnCUpdateComponent implements OnInit {
  isSaving = false;
  majorGroupCnC: IMajorGroupCnC | null = null;

  protected majorGroupCnCService = inject(MajorGroupCnCService);
  protected majorGroupCnCFormService = inject(MajorGroupCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MajorGroupCnCFormGroup = this.majorGroupCnCFormService.createMajorGroupCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ majorGroupCnC }) => {
      this.majorGroupCnC = majorGroupCnC;
      if (majorGroupCnC) {
        this.updateForm(majorGroupCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const majorGroupCnC = this.majorGroupCnCFormService.getMajorGroupCnC(this.editForm);
    if (majorGroupCnC.id !== null) {
      this.subscribeToSaveResponse(this.majorGroupCnCService.update(majorGroupCnC));
    } else {
      this.subscribeToSaveResponse(this.majorGroupCnCService.create(majorGroupCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMajorGroupCnC>>): void {
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

  protected updateForm(majorGroupCnC: IMajorGroupCnC): void {
    this.majorGroupCnC = majorGroupCnC;
    this.majorGroupCnCFormService.resetForm(this.editForm, majorGroupCnC);
  }
}
