import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllocatedRange } from 'app/shared/model/allocated-range.model';

@Component({
  selector: 'jhi-allocated-range-detail',
  templateUrl: './allocated-range-detail.component.html'
})
export class AllocatedRangeDetailComponent implements OnInit {
  allocatedRange: IAllocatedRange;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedRange }) => {
      this.allocatedRange = allocatedRange;
    });
  }

  previousState() {
    window.history.back();
  }
}
