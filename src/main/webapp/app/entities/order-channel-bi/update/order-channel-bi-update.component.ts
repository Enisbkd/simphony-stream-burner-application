import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrderChannelBI } from '../order-channel-bi.model';
import { OrderChannelBIService } from '../service/order-channel-bi.service';
import { OrderChannelBIFormGroup, OrderChannelBIFormService } from './order-channel-bi-form.service';

@Component({
  selector: 'jhi-order-channel-bi-update',
  templateUrl: './order-channel-bi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrderChannelBIUpdateComponent implements OnInit {
  isSaving = false;
  orderChannelBI: IOrderChannelBI | null = null;

  protected orderChannelBIService = inject(OrderChannelBIService);
  protected orderChannelBIFormService = inject(OrderChannelBIFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrderChannelBIFormGroup = this.orderChannelBIFormService.createOrderChannelBIFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderChannelBI }) => {
      this.orderChannelBI = orderChannelBI;
      if (orderChannelBI) {
        this.updateForm(orderChannelBI);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderChannelBI = this.orderChannelBIFormService.getOrderChannelBI(this.editForm);
    if (orderChannelBI.id !== null) {
      this.subscribeToSaveResponse(this.orderChannelBIService.update(orderChannelBI));
    } else {
      this.subscribeToSaveResponse(this.orderChannelBIService.create(orderChannelBI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderChannelBI>>): void {
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

  protected updateForm(orderChannelBI: IOrderChannelBI): void {
    this.orderChannelBI = orderChannelBI;
    this.orderChannelBIFormService.resetForm(this.editForm, orderChannelBI);
  }
}
