import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Retransmit } from 'app/shared/model/retransmit.model';
import { RetransmitService } from './retransmit.service';
import { RetransmitComponent } from './retransmit.component';
import { RetransmitDetailComponent } from './retransmit-detail.component';
import { RetransmitUpdateComponent } from './retransmit-update.component';
import { RetransmitDeletePopupComponent } from './retransmit-delete-dialog.component';
import { IRetransmit } from 'app/shared/model/retransmit.model';

@Injectable({ providedIn: 'root' })
export class RetransmitResolve implements Resolve<IRetransmit> {
  constructor(private service: RetransmitService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRetransmit> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Retransmit>) => response.ok),
        map((retransmit: HttpResponse<Retransmit>) => retransmit.body)
      );
    }
    return of(new Retransmit());
  }
}

export const retransmitRoute: Routes = [
  {
    path: '',
    component: RetransmitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.retransmit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RetransmitDetailComponent,
    resolve: {
      retransmit: RetransmitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.retransmit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RetransmitUpdateComponent,
    resolve: {
      retransmit: RetransmitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.retransmit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RetransmitUpdateComponent,
    resolve: {
      retransmit: RetransmitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.retransmit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const retransmitPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RetransmitDeletePopupComponent,
    resolve: {
      retransmit: RetransmitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.retransmit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
