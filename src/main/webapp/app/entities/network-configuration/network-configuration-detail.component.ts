import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

@Component({
  selector: 'jhi-network-configuration-detail',
  templateUrl: './network-configuration-detail.component.html'
})
export class NetworkConfigurationDetailComponent implements OnInit {
  networkConfiguration: INetworkConfiguration;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ networkConfiguration }) => {
      this.networkConfiguration = networkConfiguration;
    });
  }

  previousState() {
    window.history.back();
  }
}
