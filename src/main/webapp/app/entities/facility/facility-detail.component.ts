import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacility } from 'app/shared/model/facility.model';

@Component({
  selector: 'jhi-facility-detail',
  templateUrl: './facility-detail.component.html'
})
export class FacilityDetailComponent implements OnInit {
  facility: IFacility;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ facility }) => {
      this.facility = facility;
    });
  }

  previousState() {
    window.history.back();
  }
}
