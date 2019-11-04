import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IAllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AllocatedSceneRangeService } from './allocated-scene-range.service';

@Component({
  selector: 'jhi-allocated-scene-range',
  templateUrl: './allocated-scene-range.component.html'
})
export class AllocatedSceneRangeComponent implements OnInit, OnDestroy {
  allocatedSceneRanges: IAllocatedSceneRange[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected allocatedSceneRangeService: AllocatedSceneRangeService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService
  ) {
    this.allocatedSceneRanges = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.allocatedSceneRangeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAllocatedSceneRange[]>) => this.paginateAllocatedSceneRanges(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.allocatedSceneRanges = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAllocatedSceneRanges();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAllocatedSceneRange) {
    return item.id;
  }

  registerChangeInAllocatedSceneRanges() {
    this.eventSubscriber = this.eventManager.subscribe('allocatedSceneRangeListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAllocatedSceneRanges(data: IAllocatedSceneRange[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.allocatedSceneRanges.push(data[i]);
    }
  }
}
