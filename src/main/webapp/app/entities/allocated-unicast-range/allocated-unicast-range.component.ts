import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IAllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';
import { AccountService } from 'app/core/auth/account.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AllocatedUnicastRangeService } from './allocated-unicast-range.service';

@Component({
  selector: 'jhi-allocated-unicast-range',
  templateUrl: './allocated-unicast-range.component.html'
})
export class AllocatedUnicastRangeComponent implements OnInit, OnDestroy {
  allocatedUnicastRanges: IAllocatedUnicastRange[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected allocatedUnicastRangeService: AllocatedUnicastRangeService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService
  ) {
    this.allocatedUnicastRanges = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.allocatedUnicastRangeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAllocatedUnicastRange[]>) => this.paginateAllocatedUnicastRanges(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.allocatedUnicastRanges = [];
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
    this.registerChangeInAllocatedUnicastRanges();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAllocatedUnicastRange) {
    return item.id;
  }

  registerChangeInAllocatedUnicastRanges() {
    this.eventSubscriber = this.eventManager.subscribe('allocatedUnicastRangeListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAllocatedUnicastRanges(data: IAllocatedUnicastRange[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.allocatedUnicastRanges.push(data[i]);
    }
  }
}
