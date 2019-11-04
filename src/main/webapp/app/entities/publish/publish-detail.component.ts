import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublish } from 'app/shared/model/publish.model';

@Component({
  selector: 'jhi-publish-detail',
  templateUrl: './publish-detail.component.html'
})
export class PublishDetailComponent implements OnInit {
  publish: IPublish;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ publish }) => {
      this.publish = publish;
    });
  }

  previousState() {
    window.history.back();
  }
}
