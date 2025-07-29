import { Component, NgZone, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { FormsModule } from '@angular/forms';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { IMenuItemDefinitionsCnC } from '../menu-item-definitions-cn-c.model';
import { EntityArrayResponseType, MenuItemDefinitionsCnCService } from '../service/menu-item-definitions-cn-c.service';
import { MenuItemDefinitionsCnCDeleteDialogComponent } from '../delete/menu-item-definitions-cn-c-delete-dialog.component';

@Component({
  selector: 'jhi-menu-item-definitions-cn-c',
  templateUrl: './menu-item-definitions-cn-c.component.html',
  imports: [RouterModule, FormsModule, SharedModule, SortDirective, SortByDirective],
})
export class MenuItemDefinitionsCnCComponent implements OnInit {
  subscription: Subscription | null = null;
  menuItemDefinitionsCnCS = signal<IMenuItemDefinitionsCnC[]>([]);
  isLoading = false;

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly menuItemDefinitionsCnCService = inject(MenuItemDefinitionsCnCService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IMenuItemDefinitionsCnC): number => this.menuItemDefinitionsCnCService.getMenuItemDefinitionsCnCIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.menuItemDefinitionsCnCS().length === 0) {
            this.load();
          } else {
            this.menuItemDefinitionsCnCS.set(this.refineData(this.menuItemDefinitionsCnCS()));
          }
        }),
      )
      .subscribe();
  }

  delete(menuItemDefinitionsCnC: IMenuItemDefinitionsCnC): void {
    const modalRef = this.modalService.open(MenuItemDefinitionsCnCDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.menuItemDefinitionsCnC = menuItemDefinitionsCnC;
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
    this.menuItemDefinitionsCnCS.set(this.refineData(dataFromBody));
  }

  protected refineData(data: IMenuItemDefinitionsCnC[]): IMenuItemDefinitionsCnC[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IMenuItemDefinitionsCnC[] | null): IMenuItemDefinitionsCnC[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.menuItemDefinitionsCnCService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
