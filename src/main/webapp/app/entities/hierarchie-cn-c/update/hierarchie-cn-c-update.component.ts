import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHierarchieCnC } from '../hierarchie-cn-c.model';
import { HierarchieCnCService } from '../service/hierarchie-cn-c.service';
import { HierarchieCnCFormGroup, HierarchieCnCFormService } from './hierarchie-cn-c-form.service';

@Component({
  selector: 'jhi-hierarchie-cn-c-update',
  templateUrl: './hierarchie-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HierarchieCnCUpdateComponent implements OnInit {
  isSaving = false;
  hierarchieCnC: IHierarchieCnC | null = null;

  protected hierarchieCnCService = inject(HierarchieCnCService);
  protected hierarchieCnCFormService = inject(HierarchieCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HierarchieCnCFormGroup = this.hierarchieCnCFormService.createHierarchieCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hierarchieCnC }) => {
      this.hierarchieCnC = hierarchieCnC;
      if (hierarchieCnC) {
        this.updateForm(hierarchieCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hierarchieCnC = this.hierarchieCnCFormService.getHierarchieCnC(this.editForm);
    if (hierarchieCnC.id !== null) {
      this.subscribeToSaveResponse(this.hierarchieCnCService.update(hierarchieCnC));
    } else {
      this.subscribeToSaveResponse(this.hierarchieCnCService.create(hierarchieCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHierarchieCnC>>): void {
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

  protected updateForm(hierarchieCnC: IHierarchieCnC): void {
    this.hierarchieCnC = hierarchieCnC;
    this.hierarchieCnCFormService.resetForm(this.editForm, hierarchieCnC);
  }
}
