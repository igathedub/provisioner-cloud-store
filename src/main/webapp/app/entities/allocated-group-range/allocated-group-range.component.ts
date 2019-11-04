import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IAllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AllocatedGroupRangeService } from './allocated-group-range.service';

@Component({
  selector: 'jhi-allocated-group-range',
  templateUrl: './allocated-group-range.component.html'
})
export class AllocatedGroupRangeComponent implements OnInit, OnDestroy {
  allocatedGroupRanges: IAllocatedGroupRange[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected allocatedGroupRangeService: AllocatedGroupRangeService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService
  ) {
    this.allocatedGroupRanges = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.allocatedGroupRangeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAllocatedGroupRange[]>) => this.paginateAllocatedGroupRanges(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.allocatedGroupRanges = [];
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
    this.registerChangeInAllocatedGroupRanges();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAllocatedGroupRange) {
    return item.id;
  }

  registerChangeInAllocatedGroupRanges() {
    this.eventSubscriber = this.eventManager.subscribe('allocatedGroupRangeListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAllocatedGroupRanges(data: IAllocatedGroupRange[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.allocatedGroupRanges.push(data[i]);
    }
  }
}
