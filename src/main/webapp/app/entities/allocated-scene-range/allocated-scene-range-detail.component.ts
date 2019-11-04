import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';

@Component({
  selector: 'jhi-allocated-scene-range-detail',
  templateUrl: './allocated-scene-range-detail.component.html'
})
export class AllocatedSceneRangeDetailComponent implements OnInit {
  allocatedSceneRange: IAllocatedSceneRange;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedSceneRange }) => {
      this.allocatedSceneRange = allocatedSceneRange;
    });
  }

  previousState() {
    window.history.back();
  }
}
