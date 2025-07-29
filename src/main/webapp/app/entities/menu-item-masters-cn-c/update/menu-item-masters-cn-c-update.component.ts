import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMenuItemMastersCnC } from '../menu-item-masters-cn-c.model';
import { MenuItemMastersCnCService } from '../service/menu-item-masters-cn-c.service';
import { MenuItemMastersCnCFormGroup, MenuItemMastersCnCFormService } from './menu-item-masters-cn-c-form.service';

@Component({
  selector: 'jhi-menu-item-masters-cn-c-update',
  templateUrl: './menu-item-masters-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MenuItemMastersCnCUpdateComponent implements OnInit {
  isSaving = false;
  menuItemMastersCnC: IMenuItemMastersCnC | null = null;

  protected menuItemMastersCnCService = inject(MenuItemMastersCnCService);
  protected menuItemMastersCnCFormService = inject(MenuItemMastersCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MenuItemMastersCnCFormGroup = this.menuItemMastersCnCFormService.createMenuItemMastersCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuItemMastersCnC }) => {
      this.menuItemMastersCnC = menuItemMastersCnC;
      if (menuItemMastersCnC) {
        this.updateForm(menuItemMastersCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menuItemMastersCnC = this.menuItemMastersCnCFormService.getMenuItemMastersCnC(this.editForm);
    if (menuItemMastersCnC.id !== null) {
      this.subscribeToSaveResponse(this.menuItemMastersCnCService.update(menuItemMastersCnC));
    } else {
      this.subscribeToSaveResponse(this.menuItemMastersCnCService.create(menuItemMastersCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenuItemMastersCnC>>): void {
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

  protected updateForm(menuItemMastersCnC: IMenuItemMastersCnC): void {
    this.menuItemMastersCnC = menuItemMastersCnC;
    this.menuItemMastersCnCFormService.resetForm(this.editForm, menuItemMastersCnC);
  }
}
