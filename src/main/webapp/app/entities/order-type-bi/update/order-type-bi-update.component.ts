import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrderTypeBI } from '../order-type-bi.model';
import { OrderTypeBIService } from '../service/order-type-bi.service';
import { OrderTypeBIFormGroup, OrderTypeBIFormService } from './order-type-bi-form.service';

@Component({
  selector: 'jhi-order-type-bi-update',
  templateUrl: './order-type-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrderTypeBIUpdateComponent implements OnInit {
  isSaving = false;
  orderTypeBI: IOrderTypeBI | null = null;

  protected orderTypeBIService = inject(OrderTypeBIService);
  protected orderTypeBIFormService = inject(OrderTypeBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrderTypeBIFormGroup = this.orderTypeBIFormService.createOrderTypeBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTypeBI }) => {
      this.orderTypeBI = orderTypeBI;
      if (orderTypeBI) {
        this.updateForm(orderTypeBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderTypeBI = this.orderTypeBIFormService.getOrderTypeBI(this.editForm);
    if (orderTypeBI.id !== null) {
      this.subscribeToSaveResponse(this.orderTypeBIService.update(orderTypeBI));
    } else {
      this.subscribeToSaveResponse(this.orderTypeBIService.create(orderTypeBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTypeBI>>): void {
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

  protected updateForm(orderTypeBI: IOrderTypeBI): void {
    this.orderTypeBI = orderTypeBI;
    this.orderTypeBIFormService.resetForm(this.editForm, orderTypeBI);
  }
}
