import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeatures } from 'app/shared/model/features.model';
import { FeaturesService } from './features.service';

@Component({
  selector: 'jhi-features-delete-dialog',
  templateUrl: './features-delete-dialog.component.html'
})
export class FeaturesDeleteDialogComponent {
  features: IFeatures;

  constructor(protected featuresService: FeaturesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.featuresService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'featuresListModification',
        content: 'Deleted an features'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-features-delete-popup',
  template: ''
})
export class FeaturesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ features }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FeaturesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.features = features;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/features', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/features', { outlets: { popup: null } }]);
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
