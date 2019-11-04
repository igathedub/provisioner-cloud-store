import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeshGroup } from 'app/shared/model/mesh-group.model';

@Component({
  selector: 'jhi-mesh-group-detail',
  templateUrl: './mesh-group-detail.component.html'
})
export class MeshGroupDetailComponent implements OnInit {
  meshGroup: IMeshGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meshGroup }) => {
      this.meshGroup = meshGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
