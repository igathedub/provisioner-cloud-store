import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from './network-configuration.service';

@Component({
  selector: 'jhi-network-configuration-delete-dialog',
  templateUrl: './network-configuration-delete-dialog.component.html'
})
export class NetworkConfigurationDeleteDialogComponent {
  networkConfiguration: INetworkConfiguration;

  constructor(
    protected networkConfigurationService: NetworkConfigurationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.networkConfigurationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'networkConfigurationListModification',
        content: 'Deleted an networkConfiguration'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-network-configuration-delete-popup',
  template: ''
})
export class NetworkConfigurationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ networkConfiguration }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NetworkConfigurationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.networkConfiguration = networkConfiguration;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/network-configuration', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/network-configuration', { outlets: { popup: null } }]);
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
