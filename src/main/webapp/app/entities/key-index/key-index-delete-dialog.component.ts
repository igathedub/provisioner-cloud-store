import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKeyIndex } from 'app/shared/model/key-index.model';
import { KeyIndexService } from './key-index.service';

@Component({
  selector: 'jhi-key-index-delete-dialog',
  templateUrl: './key-index-delete-dialog.component.html'
})
export class KeyIndexDeleteDialogComponent {
  keyIndex: IKeyIndex;

  constructor(protected keyIndexService: KeyIndexService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.keyIndexService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'keyIndexListModification',
        content: 'Deleted an keyIndex'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-key-index-delete-popup',
  template: ''
})
export class KeyIndexDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ keyIndex }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(KeyIndexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.keyIndex = keyIndex;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/key-index', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/key-index', { outlets: { popup: null } }]);
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
