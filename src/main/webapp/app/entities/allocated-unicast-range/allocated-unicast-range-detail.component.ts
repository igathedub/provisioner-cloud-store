import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';

@Component({
  selector: 'jhi-allocated-unicast-range-detail',
  templateUrl: './allocated-unicast-range-detail.component.html'
})
export class AllocatedUnicastRangeDetailComponent implements OnInit {
  allocatedUnicastRange: IAllocatedUnicastRange;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedUnicastRange }) => {
      this.allocatedUnicastRange = allocatedUnicastRange;
    });
  }

  previousState() {
    window.history.back();
  }
}
