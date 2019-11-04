import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElement } from 'app/shared/model/element.model';

@Component({
  selector: 'jhi-element-detail',
  templateUrl: './element-detail.component.html'
})
export class ElementDetailComponent implements OnInit {
  element: IElement;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ element }) => {
      this.element = element;
    });
  }

  previousState() {
    window.history.back();
  }
}
