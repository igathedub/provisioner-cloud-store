import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';
import { AllocatedSceneRangeService } from './allocated-scene-range.service';

@Component({
  selector: 'jhi-allocated-scene-range-delete-dialog',
  templateUrl: './allocated-scene-range-delete-dialog.component.html'
})
export class AllocatedSceneRangeDeleteDialogComponent {
  allocatedSceneRange: IAllocatedSceneRange;

  constructor(
    protected allocatedSceneRangeService: AllocatedSceneRangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allocatedSceneRangeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allocatedSceneRangeListModification',
        content: 'Deleted an allocatedSceneRange'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allocated-scene-range-delete-popup',
  template: ''
})
export class AllocatedSceneRangeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocatedSceneRange }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllocatedSceneRangeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.allocatedSceneRange = allocatedSceneRange;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allocated-scene-range', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allocated-scene-range', { outlets: { popup: null } }]);
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
