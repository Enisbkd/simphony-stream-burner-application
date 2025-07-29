import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICodeRaisonBI } from '../code-raison-bi.model';
import { CodeRaisonBIService } from '../service/code-raison-bi.service';
import { CodeRaisonBIFormGroup, CodeRaisonBIFormService } from './code-raison-bi-form.service';

@Component({
  selector: 'jhi-code-raison-bi-update',
  templateUrl: './code-raison-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CodeRaisonBIUpdateComponent implements OnInit {
  isSaving = false;
  codeRaisonBI: ICodeRaisonBI | null = null;

  protected codeRaisonBIService = inject(CodeRaisonBIService);
  protected codeRaisonBIFormService = inject(CodeRaisonBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CodeRaisonBIFormGroup = this.codeRaisonBIFormService.createCodeRaisonBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeRaisonBI }) => {
      this.codeRaisonBI = codeRaisonBI;
      if (codeRaisonBI) {
        this.updateForm(codeRaisonBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codeRaisonBI = this.codeRaisonBIFormService.getCodeRaisonBI(this.editForm);
    if (codeRaisonBI.id !== null) {
      this.subscribeToSaveResponse(this.codeRaisonBIService.update(codeRaisonBI));
    } else {
      this.subscribeToSaveResponse(this.codeRaisonBIService.create(codeRaisonBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodeRaisonBI>>): void {
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

  protected updateForm(codeRaisonBI: ICodeRaisonBI): void {
    this.codeRaisonBI = codeRaisonBI;
    this.codeRaisonBIFormService.resetForm(this.editForm, codeRaisonBI);
  }
}
