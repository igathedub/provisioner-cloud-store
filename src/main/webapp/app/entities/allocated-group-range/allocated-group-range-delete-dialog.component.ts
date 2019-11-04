import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';
import { AllocatedGroupRangeService } from './allocated-group-range.service';

@Component({
  selector: 'jhi-allocated-group-range-delete-dialog',
  templateUrl: './allocated-group-range-delete-dialog.component.html'
})
export class AllocatedGroupRangeDeleteDialogComponent {
  allocatedGroupRange: IAllocatedGroupRange;

  constructor(
    protected allocatedGroupRangeService: AllocatedGroupRangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allocatedGroupRangeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allocatedGroupRangeListModification',
        content: 'Deleted an allocatedGroupRange'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allocated-group-range-delete-popup',
  template: ''
})
export class AllocatedGroupRangeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedGroupRange }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllocatedGroupRangeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.allocatedGroupRange = allocatedGroupRange;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allocated-group-range', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allocated-group-range', { outlets: { popup: null } }]);
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
