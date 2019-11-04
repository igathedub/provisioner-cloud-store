import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvisioner } from 'app/shared/model/provisioner.model';

@Component({
  selector: 'jhi-provisioner-detail',
  templateUrl: './provisioner-detail.component.html'
})
export class ProvisionerDetailComponent implements OnInit {
  provisioner: IProvisioner;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ provisioner }) => {
      this.provisioner = provisioner;
    });
  }

  previousState() {
    window.history.back();
  }
}
