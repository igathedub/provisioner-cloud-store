import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INetKeyIndex } from 'app/shared/model/net-key-index.model';
import { NetKeyIndexService } from './net-key-index.service';

@Component({
  selector: 'jhi-net-key-index-delete-dialog',
  templateUrl: './net-key-index-delete-dialog.component.html'
})
export class NetKeyIndexDeleteDialogComponent {
  netKeyIndex: INetKeyIndex;

  constructor(
    protected netKeyIndexService: NetKeyIndexService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.netKeyIndexService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'netKeyIndexListModification',
        content: 'Deleted an netKeyIndex'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-net-key-index-delete-popup',
  template: ''
})
export class NetKeyIndexDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ netKeyIndex }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NetKeyIndexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.netKeyIndex = netKeyIndex;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/net-key-index', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/net-key-index', { outlets: { popup: null } }]);
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
