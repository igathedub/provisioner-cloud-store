import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AppKey } from 'app/shared/model/app-key.model';
import { AppKeyService } from './app-key.service';
import { AppKeyComponent } from './app-key.component';
import { AppKeyDetailComponent } from './app-key-detail.component';
import { AppKeyUpdateComponent } from './app-key-update.component';
import { AppKeyDeletePopupComponent } from './app-key-delete-dialog.component';
import { IAppKey } from 'app/shared/model/app-key.model';

@Injectable({ providedIn: 'root' })
export class AppKeyResolve implements Resolve<IAppKey> {
  constructor(private service: AppKeyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppKey> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AppKey>) => response.ok),
        map((appKey: HttpResponse<AppKey>) => appKey.body)
      );
    }
    return of(new AppKey());
  }
}

export const appKeyRoute: Routes = [
  {
    path: '',
    component: AppKeyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.appKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppKeyDetailComponent,
    resolve: {
      appKey: AppKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.appKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppKeyUpdateComponent,
    resolve: {
      appKey: AppKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.appKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppKeyUpdateComponent,
    resolve: {
      appKey: AppKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.appKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const appKeyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AppKeyDeletePopupComponent,
    resolve: {
      appKey: AppKeyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.appKey.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
