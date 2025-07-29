import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';
import { MenuItemDefinitionsCnCService } from '../service/menu-item-definitions-cn-c.service';
import { MenuItemDefinitionsCnCFormGroup, MenuItemDefinitionsCnCFormService } from './menu-item-definitions-cn-c-form.service';

@Component({
  selector: 'jhi-menu-item-definitions-cn-c-update',
  templateUrl: './menu-item-definitions-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MenuItemDefinitionsCnCUpdateComponent implements OnInit {
  isSaving = false;
  menuItemDefinitionsCnC: IMenuItemDefinitionsCnC | null = null;

  protected menuItemDefinitionsCnCService = inject(MenuItemDefinitionsCnCService);
  protected menuItemDefinitionsCnCFormService = inject(MenuItemDefinitionsCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MenuItemDefinitionsCnCFormGroup = this.menuItemDefinitionsCnCFormService.createMenuItemDefinitionsCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuItemDefinitionsCnC }) => {
      this.menuItemDefinitionsCnC = menuItemDefinitionsCnC;
      if (menuItemDefinitionsCnC) {
        this.updateForm(menuItemDefinitionsCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menuItemDefinitionsCnC = this.menuItemDefinitionsCnCFormService.getMenuItemDefinitionsCnC(this.editForm);
    if (menuItemDefinitionsCnC.id !== null) {
      this.subscribeToSaveResponse(this.menuItemDefinitionsCnCService.update(menuItemDefinitionsCnC));
    } else {
      this.subscribeToSaveResponse(this.menuItemDefinitionsCnCService.create(menuItemDefinitionsCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenuItemDefinitionsCnC>>): void {
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

  protected updateForm(menuItemDefinitionsCnC: IMenuItemDefinitionsCnC): void {
    this.menuItemDefinitionsCnC = menuItemDefinitionsCnC;
    this.menuItemDefinitionsCnCFormService.resetForm(this.editForm, menuItemDefinitionsCnC);
  }
}
