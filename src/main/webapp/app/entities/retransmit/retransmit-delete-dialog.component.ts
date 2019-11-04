import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRetransmit } from 'app/shared/model/retransmit.model';
import { RetransmitService } from './retransmit.service';

@Component({
  selector: 'jhi-retransmit-delete-dialog',
  templateUrl: './retransmit-delete-dialog.component.html'
})
export class RetransmitDeleteDialogComponent {
  retransmit: IRetransmit;

  constructor(
    protected retransmitService: RetransmitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.retransmitService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'retransmitListModification',
        content: 'Deleted an retransmit'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-retransmit-delete-popup',
  template: ''
})
export class RetransmitDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ retransmit }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RetransmitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.retransmit = retransmit;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/retransmit', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/retransmit', { outlets: { popup: null } }]);
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
