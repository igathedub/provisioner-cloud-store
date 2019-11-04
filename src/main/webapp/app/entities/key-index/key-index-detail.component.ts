import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKeyIndex } from 'app/shared/model/key-index.model';

@Component({
  selector: 'jhi-key-index-detail',
  templateUrl: './key-index-detail.component.html'
})
export class KeyIndexDetailComponent implements OnInit {
  keyIndex: IKeyIndex;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ keyIndex }) => {
      this.keyIndex = keyIndex;
    });
  }

  previousState() {
    window.history.back();
  }
}
