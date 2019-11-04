import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeshGroup } from 'app/shared/model/mesh-group.model';
import { MeshGroupService } from './mesh-group.service';

@Component({
  selector: 'jhi-mesh-group-delete-dialog',
  templateUrl: './mesh-group-delete-dialog.component.html'
})
export class MeshGroupDeleteDialogComponent {
  meshGroup: IMeshGroup;

  constructor(protected meshGroupService: MeshGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.meshGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'meshGroupListModification',
        content: 'Deleted an meshGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mesh-group-delete-popup',
  template: ''
})
export class MeshGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meshGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MeshGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.meshGroup = meshGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/mesh-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/mesh-group', { outlets: { popup: null } }]);
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
