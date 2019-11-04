import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INode } from 'app/shared/model/node.model';
import { NodeService } from './node.service';

@Component({
  selector: 'jhi-node-delete-dialog',
  templateUrl: './node-delete-dialog.component.html'
})
export class NodeDeleteDialogComponent {
  node: INode;

  constructor(protected nodeService: NodeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nodeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nodeListModification',
        content: 'Deleted an node'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-node-delete-popup',
  template: ''
})
export class NodeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ node }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NodeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.node = node;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/node', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/node', { outlets: { popup: null } }]);
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
