import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRetransmit } from 'app/shared/model/retransmit.model';

@Component({
  selector: 'jhi-retransmit-detail',
  templateUrl: './retransmit-detail.component.html'
})
export class RetransmitDetailComponent implements OnInit {
  retransmit: IRetransmit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ retransmit }) => {
      this.retransmit = retransmit;
    });
  }

  previousState() {
    window.history.back();
  }
}
