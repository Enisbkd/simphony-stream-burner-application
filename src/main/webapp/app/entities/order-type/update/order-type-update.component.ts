import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrderType } from '../order-type.model';
import { OrderTypeService } from '../service/order-type.service';
import { OrderTypeFormGroup, OrderTypeFormService } from './order-type-form.service';

@Component({
  selector: 'jhi-order-type-update',
  templateUrl: './order-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrderTypeUpdateComponent implements OnInit {
  isSaving = false;
  orderType: IOrderType | null = null;

  protected orderTypeService = inject(OrderTypeService);
  protected orderTypeFormService = inject(OrderTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrderTypeFormGroup = this.orderTypeFormService.createOrderTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderType }) => {
      this.orderType = orderType;
      if (orderType) {
        this.updateForm(orderType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderType = this.orderTypeFormService.getOrderType(this.editForm);
    if (orderType.id !== null) {
      this.subscribeToSaveResponse(this.orderTypeService.update(orderType));
    } else {
      this.subscribeToSaveResponse(this.orderTypeService.create(orderType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderType>>): void {
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

  protected updateForm(orderType: IOrderType): void {
    this.orderType = orderType;
    this.orderTypeFormService.resetForm(this.editForm, orderType);
  }
}
