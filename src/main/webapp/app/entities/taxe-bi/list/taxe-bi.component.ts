import { Component, NgZone, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { FormsModule } from '@angular/forms';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { ITaxeBI } from '../taxe-bi.model';
import { EntityArrayResponseType, TaxeBIService } from '../service/taxe-bi.service';
import { TaxeBIDeleteDialogComponent } from '../delete/taxe-bi-delete-dialog.component';

@Component({
  selector: 'jhi-taxe-bi',
  templateUrl: './taxe-bi.component.html',
  imports: [RouterModule, FormsModule, SharedModule, SortDirective, SortByDirective],
})
export class TaxeBIComponent implements OnInit {
  subscription: Subscription | null = null;
  taxeBIS = signal<ITaxeBI[]>([]);
  isLoading = false;

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly taxeBIService = inject(TaxeBIService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: ITaxeBI): number => this.taxeBIService.getTaxeBIIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.taxeBIS().length === 0) {
            this.load();
          } else {
            this.taxeBIS.set(this.refineData(this.taxeBIS()));
          }
        }),
      )
      .subscribe();
  }

  delete(taxeBI: ITaxeBI): void {
    const modalRef = this.modalService.open(TaxeBIDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxeBI = taxeBI;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.taxeBIS.set(this.refineData(dataFromBody));
  }

  protected refineData(data: ITaxeBI[]): ITaxeBI[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ITaxeBI[] | null): ITaxeBI[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.taxeBIService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(sortState: SortState): void {
    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
