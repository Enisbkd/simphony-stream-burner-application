import { Component, NgZone, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { FormsModule } from '@angular/forms';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { IPointDeVenteCnC } from '../point-de-vente-cn-c.model';
import { EntityArrayResponseType, PointDeVenteCnCService } from '../service/point-de-vente-cn-c.service';
import { PointDeVenteCnCDeleteDialogComponent } from '../delete/point-de-vente-cn-c-delete-dialog.component';

@Component({
  selector: 'jhi-point-de-vente-cn-c',
  templateUrl: './point-de-vente-cn-c.component.html',
  imports: [RouterModule, FormsModule, SharedModule, SortDirective, SortByDirective],
})
export class PointDeVenteCnCComponent implements OnInit {
  subscription: Subscription | null = null;
  pointDeVenteCnCS = signal<IPointDeVenteCnC[]>([]);
  isLoading = false;

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly pointDeVenteCnCService = inject(PointDeVenteCnCService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IPointDeVenteCnC): number => this.pointDeVenteCnCService.getPointDeVenteCnCIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.pointDeVenteCnCS().length === 0) {
            this.load();
          } else {
            this.pointDeVenteCnCS.set(this.refineData(this.pointDeVenteCnCS()));
          }
        }),
      )
      .subscribe();
  }

  delete(pointDeVenteCnC: IPointDeVenteCnC): void {
    const modalRef = this.modalService.open(PointDeVenteCnCDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pointDeVenteCnC = pointDeVenteCnC;
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
    this.pointDeVenteCnCS.set(this.refineData(dataFromBody));
  }

  protected refineData(data: IPointDeVenteCnC[]): IPointDeVenteCnC[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IPointDeVenteCnC[] | null): IPointDeVenteCnC[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.pointDeVenteCnCService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
