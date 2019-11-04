import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INetKey } from 'app/shared/model/net-key.model';
import { NetKeyService } from './net-key.service';

@Component({
  selector: 'jhi-net-key-delete-dialog',
  templateUrl: './net-key-delete-dialog.component.html'
})
export class NetKeyDeleteDialogComponent {
  netKey: INetKey;

  constructor(protected netKeyService: NetKeyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.netKeyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'netKeyListModification',
        content: 'Deleted an netKey'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-net-key-delete-popup',
  template: ''
})
export class NetKeyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ netKey }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NetKeyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.netKey = netKey;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/net-key', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/net-key', { outlets: { popup: null } }]);
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
