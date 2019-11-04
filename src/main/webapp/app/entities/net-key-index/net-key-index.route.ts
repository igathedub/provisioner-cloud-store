import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NetKeyIndex } from 'app/shared/model/net-key-index.model';
import { NetKeyIndexService } from './net-key-index.service';
import { NetKeyIndexComponent } from './net-key-index.component';
import { NetKeyIndexDetailComponent } from './net-key-index-detail.component';
import { NetKeyIndexUpdateComponent } from './net-key-index-update.component';
import { NetKeyIndexDeletePopupComponent } from './net-key-index-delete-dialog.component';
import { INetKeyIndex } from 'app/shared/model/net-key-index.model';

@Injectable({ providedIn: 'root' })
export class NetKeyIndexResolve implements Resolve<INetKeyIndex> {
  constructor(private service: NetKeyIndexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INetKeyIndex> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NetKeyIndex>) => response.ok),
        map((netKeyIndex: HttpResponse<NetKeyIndex>) => netKeyIndex.body)
      );
    }
    return of(new NetKeyIndex());
  }
}

export const netKeyIndexRoute: Routes = [
  {
    path: '',
    component: NetKeyIndexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKeyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NetKeyIndexDetailComponent,
    resolve: {
      netKeyIndex: NetKeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKeyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NetKeyIndexUpdateComponent,
    resolve: {
      netKeyIndex: NetKeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKeyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NetKeyIndexUpdateComponent,
    resolve: {
      netKeyIndex: NetKeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKeyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const netKeyIndexPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NetKeyIndexDeletePopupComponent,
    resolve: {
      netKeyIndex: NetKeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.netKeyIndex.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
