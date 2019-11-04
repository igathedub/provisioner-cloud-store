import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from './app-user.service';

@Component({
  selector: 'jhi-app-user-delete-dialog',
  templateUrl: './app-user-delete-dialog.component.html'
})
export class AppUserDeleteDialogComponent {
  appUser: IAppUser;

  constructor(protected appUserService: AppUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appUserListModification',
        content: 'Deleted an appUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-app-user-delete-popup',
  template: ''
})
export class AppUserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.appUser = appUser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/app-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/app-user', { outlets: { popup: null } }]);
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
