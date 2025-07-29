import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMenuItemPricesCnC } from '../menu-item-prices-cn-c.model';
import { MenuItemPricesCnCService } from '../service/menu-item-prices-cn-c.service';
import { MenuItemPricesCnCFormGroup, MenuItemPricesCnCFormService } from './menu-item-prices-cn-c-form.service';

@Component({
  selector: 'jhi-menu-item-prices-cn-c-update',
  templateUrl: './menu-item-prices-cn-c-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MenuItemPricesCnCUpdateComponent implements OnInit {
  isSaving = false;
  menuItemPricesCnC: IMenuItemPricesCnC | null = null;

  protected menuItemPricesCnCService = inject(MenuItemPricesCnCService);
  protected menuItemPricesCnCFormService = inject(MenuItemPricesCnCFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MenuItemPricesCnCFormGroup = this.menuItemPricesCnCFormService.createMenuItemPricesCnCFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuItemPricesCnC }) => {
      this.menuItemPricesCnC = menuItemPricesCnC;
      if (menuItemPricesCnC) {
        this.updateForm(menuItemPricesCnC);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menuItemPricesCnC = this.menuItemPricesCnCFormService.getMenuItemPricesCnC(this.editForm);
    if (menuItemPricesCnC.id !== null) {
      this.subscribeToSaveResponse(this.menuItemPricesCnCService.update(menuItemPricesCnC));
    } else {
      this.subscribeToSaveResponse(this.menuItemPricesCnCService.create(menuItemPricesCnC));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenuItemPricesCnC>>): void {
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

  protected updateForm(menuItemPricesCnC: IMenuItemPricesCnC): void {
    this.menuItemPricesCnC = menuItemPricesCnC;
    this.menuItemPricesCnCFormService.resetForm(this.editForm, menuItemPricesCnC);
  }
}
