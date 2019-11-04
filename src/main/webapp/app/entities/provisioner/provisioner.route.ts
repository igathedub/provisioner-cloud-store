import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Provisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from './provisioner.service';
import { ProvisionerComponent } from './provisioner.component';
import { ProvisionerDetailComponent } from './provisioner-detail.component';
import { ProvisionerUpdateComponent } from './provisioner-update.component';
import { ProvisionerDeletePopupComponent } from './provisioner-delete-dialog.component';
import { IProvisioner } from 'app/shared/model/provisioner.model';

@Injectable({ providedIn: 'root' })
export class ProvisionerResolve implements Resolve<IProvisioner> {
  constructor(private service: ProvisionerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProvisioner> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Provisioner>) => response.ok),
        map((provisioner: HttpResponse<Provisioner>) => provisioner.body)
      );
    }
    return of(new Provisioner());
  }
}

export const provisionerRoute: Routes = [
  {
    path: '',
    component: ProvisionerComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.provisioner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProvisionerDetailComponent,
    resolve: {
      provisioner: ProvisionerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.provisioner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProvisionerUpdateComponent,
    resolve: {
      provisioner: ProvisionerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.provisioner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProvisionerUpdateComponent,
    resolve: {
      provisioner: ProvisionerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.provisioner.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const provisionerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProvisionerDeletePopupComponent,
    resolve: {
      provisioner: ProvisionerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.provisioner.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
