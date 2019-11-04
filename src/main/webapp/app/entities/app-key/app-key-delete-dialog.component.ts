import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppKey } from 'app/shared/model/app-key.model';
import { AppKeyService } from './app-key.service';

@Component({
  selector: 'jhi-app-key-delete-dialog',
  templateUrl: './app-key-delete-dialog.component.html'
})
export class AppKeyDeleteDialogComponent {
  appKey: IAppKey;

  constructor(protected appKeyService: AppKeyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appKeyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appKeyListModification',
        content: 'Deleted an appKey'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-app-key-delete-popup',
  template: ''
})
export class AppKeyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appKey }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppKeyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.appKey = appKey;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/app-key', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/app-key', { outlets: { popup: null } }]);
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
