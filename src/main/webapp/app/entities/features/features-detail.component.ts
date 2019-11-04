import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeatures } from 'app/shared/model/features.model';

@Component({
  selector: 'jhi-features-detail',
  templateUrl: './features-detail.component.html'
})
export class FeaturesDetailComponent implements OnInit {
  features: IFeatures;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ features }) => {
      this.features = features;
    });
  }

  previousState() {
    window.history.back();
  }
}
