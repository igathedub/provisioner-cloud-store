import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Publish } from 'app/shared/model/publish.model';
import { PublishService } from './publish.service';
import { PublishComponent } from './publish.component';
import { PublishDetailComponent } from './publish-detail.component';
import { PublishUpdateComponent } from './publish-update.component';
import { PublishDeletePopupComponent } from './publish-delete-dialog.component';
import { IPublish } from 'app/shared/model/publish.model';

@Injectable({ providedIn: 'root' })
export class PublishResolve implements Resolve<IPublish> {
  constructor(private service: PublishService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPublish> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Publish>) => response.ok),
        map((publish: HttpResponse<Publish>) => publish.body)
      );
    }
    return of(new Publish());
  }
}

export const publishRoute: Routes = [
  {
    path: '',
    component: PublishComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.publish.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PublishDetailComponent,
    resolve: {
      publish: PublishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.publish.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PublishUpdateComponent,
    resolve: {
      publish: PublishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.publish.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PublishUpdateComponent,
    resolve: {
      publish: PublishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.publish.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const publishPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PublishDeletePopupComponent,
    resolve: {
      publish: PublishResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.publish.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
