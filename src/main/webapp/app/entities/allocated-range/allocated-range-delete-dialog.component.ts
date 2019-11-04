import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllocatedRange } from 'app/shared/model/allocated-range.model';
import { AllocatedRangeService } from './allocated-range.service';

@Component({
  selector: 'jhi-allocated-range-delete-dialog',
  templateUrl: './allocated-range-delete-dialog.component.html'
})
export class AllocatedRangeDeleteDialogComponent {
  allocatedRange: IAllocatedRange;

  constructor(
    protected allocatedRangeService: AllocatedRangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allocatedRangeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allocatedRangeListModification',
        content: 'Deleted an allocatedRange'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allocated-range-delete-popup',
  template: ''
})
export class AllocatedRangeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedRange }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllocatedRangeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.allocatedRange = allocatedRange;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allocated-range', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allocated-range', { outlets: { popup: null } }]);
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
