import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElement } from 'app/shared/model/element.model';
import { ElementService } from './element.service';

@Component({
  selector: 'jhi-element-delete-dialog',
  templateUrl: './element-delete-dialog.component.html'
})
export class ElementDeleteDialogComponent {
  element: IElement;

  constructor(protected elementService: ElementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.elementService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'elementListModification',
        content: 'Deleted an element'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-element-delete-popup',
  template: ''
})
export class ElementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ element }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ElementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.element = element;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/element', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/element', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
