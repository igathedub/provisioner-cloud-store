import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetKey } from 'app/shared/model/net-key.model';

@Component({
  selector: 'jhi-net-key-detail',
  templateUrl: './net-key-detail.component.html'
})
export class NetKeyDetailComponent implements OnInit {
  netKey: INetKey;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ netKey }) => {
      this.netKey = netKey;
    });
  }

  previousState() {
    window.history.back();
  }
}
