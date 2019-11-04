import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProvisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from './provisioner.service';

@Component({
  selector: 'jhi-provisioner-delete-dialog',
  templateUrl: './provisioner-delete-dialog.component.html'
})
export class ProvisionerDeleteDialogComponent {
  provisioner: IProvisioner;

  constructor(
    protected provisionerService: ProvisionerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.provisionerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'provisionerListModification',
        content: 'Deleted an provisioner'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-provisioner-delete-popup',
  template: ''
})
export class ProvisionerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ provisioner }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProvisionerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.provisioner = provisioner;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/provisioner', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/provisioner', { outlets: { popup: null } }]);
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
