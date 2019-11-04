import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppKey } from 'app/shared/model/app-key.model';

@Component({
  selector: 'jhi-app-key-detail',
  templateUrl: './app-key-detail.component.html'
})
export class AppKeyDetailComponent implements OnInit {
  appKey: IAppKey;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appKey }) => {
      this.appKey = appKey;
    });
  }

  previousState() {
    window.history.back();
  }
}
