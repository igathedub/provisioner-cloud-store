import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';
import { AllocatedUnicastRangeService } from './allocated-unicast-range.service';

@Component({
  selector: 'jhi-allocated-unicast-range-delete-dialog',
  templateUrl: './allocated-unicast-range-delete-dialog.component.html'
})
export class AllocatedUnicastRangeDeleteDialogComponent {
  allocatedUnicastRange: IAllocatedUnicastRange;

  constructor(
    protected allocatedUnicastRangeService: AllocatedUnicastRangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allocatedUnicastRangeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allocatedUnicastRangeListModification',
        content: 'Deleted an allocatedUnicastRange'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allocated-unicast-range-delete-popup',
  template: ''
})
export class AllocatedUnicastRangeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedUnicastRange }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllocatedUnicastRangeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.allocatedUnicastRange = allocatedUnicastRange;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allocated-unicast-range', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allocated-unicast-range', { outlets: { popup: null } }]);
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
