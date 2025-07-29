import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOrderChannel } from '../order-channel.model';
import { OrderChannelService } from '../service/order-channel.service';
import { OrderChannelFormGroup, OrderChannelFormService } from './order-channel-form.service';

@Component({
  selector: 'jhi-order-channel-update',
  templateUrl: './order-channel-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrderChannelUpdateComponent implements OnInit {
  isSaving = false;
  orderChannel: IOrderChannel | null = null;

  protected orderChannelService = inject(OrderChannelService);
  protected orderChannelFormService = inject(OrderChannelFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrderChannelFormGroup = this.orderChannelFormService.createOrderChannelFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderChannel }) => {
      this.orderChannel = orderChannel;
      if (orderChannel) {
        this.updateForm(orderChannel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderChannel = this.orderChannelFormService.getOrderChannel(this.editForm);
    if (orderChannel.id !== null) {
      this.subscribeToSaveResponse(this.orderChannelService.update(orderChannel));
    } else {
      this.subscribeToSaveResponse(this.orderChannelService.create(orderChannel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderChannel>>): void {
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

  protected updateForm(orderChannel: IOrderChannel): void {
    this.orderChannel = orderChannel;
    this.orderChannelFormService.resetForm(this.editForm, orderChannel);
  }
}
