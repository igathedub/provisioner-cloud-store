import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NetKey } from 'app/shared/model/net-key.model';
import { NetKeyService } from './net-key.service';
import { NetKeyComponent } from './net-key.component';
import { NetKeyDetailComponent } from './net-key-detail.component';
import { NetKeyUpdateComponent } from './net-key-update.component';
import { NetKeyDeletePopupComponent } from './net-key-delete-dialog.component';
import { INetKey } from 'app/shared/model/net-key.model';

@Injectable({ providedIn: 'root' })
export class NetKeyResolve implements Resolve<INetKey> {
  constructor(private service: NetKeyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INetKey> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NetKey>) => response.ok),
        map((netKey: HttpResponse<NetKey>) => netKey.body)
      );
    }
    return of(new NetKey());
  }
}

export const netKeyRoute: Routes = [
  {
    path: '',
    component: NetKeyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NetKeyDetailComponent,
    resolve: {
      netKey: NetKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NetKeyUpdateComponent,
    resolve: {
      netKey: NetKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NetKeyUpdateComponent,
    resolve: {
      netKey: NetKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const netKeyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NetKeyDeletePopupComponent,
    resolve: {
      netKey: NetKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKey.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
