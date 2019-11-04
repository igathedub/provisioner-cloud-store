import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetKeyIndex } from 'app/shared/model/net-key-index.model';

@Component({
  selector: 'jhi-net-key-index-detail',
  templateUrl: './net-key-index-detail.component.html'
})
export class NetKeyIndexDetailComponent implements OnInit {
  netKeyIndex: INetKeyIndex;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ netKeyIndex }) => {
      this.netKeyIndex = netKeyIndex;
    });
  }

  previousState() {
    window.history.back();
  }
}
