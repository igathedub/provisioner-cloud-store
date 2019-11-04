import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPublish } from 'app/shared/model/publish.model';
import { PublishService } from './publish.service';

@Component({
  selector: 'jhi-publish-delete-dialog',
  templateUrl: './publish-delete-dialog.component.html'
})
export class PublishDeleteDialogComponent {
  publish: IPublish;

  constructor(protected publishService: PublishService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.publishService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'publishListModification',
        content: 'Deleted an publish'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-publish-delete-popup',
  template: ''
})
export class PublishDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ publish }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PublishDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.publish = publish;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/publish', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/publish', { outlets: { popup: null } }]);
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
