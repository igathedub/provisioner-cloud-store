import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';

@Component({
  selector: 'jhi-allocated-group-range-detail',
  templateUrl: './allocated-group-range-detail.component.html'
})
export class AllocatedGroupRangeDetailComponent implements OnInit {
  allocatedGroupRange: IAllocatedGroupRange;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedGroupRange }) => {
      this.allocatedGroupRange = allocatedGroupRange;
    });
  }

  previousState() {
    window.history.back();
  }
}
