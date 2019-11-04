import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';
import { AllocatedGroupRangeService } from './allocated-group-range.service';
import { AllocatedGroupRangeComponent } from './allocated-group-range.component';
import { AllocatedGroupRangeDetailComponent } from './allocated-group-range-detail.component';
import { AllocatedGroupRangeUpdateComponent } from './allocated-group-range-update.component';
import { AllocatedGroupRangeDeletePopupComponent } from './allocated-group-range-delete-dialog.component';
import { IAllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';

@Injectable({ providedIn: 'root' })
export class AllocatedGroupRangeResolve implements Resolve<IAllocatedGroupRange> {
  constructor(private service: AllocatedGroupRangeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllocatedGroupRange> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AllocatedGroupRange>) => response.ok),
        map((allocatedGroupRange: HttpResponse<AllocatedGroupRange>) => allocatedGroupRange.body)
      );
    }
    return of(new AllocatedGroupRange());
  }
}

export const allocatedGroupRangeRoute: Routes = [
  {
    path: '',
    component: AllocatedGroupRangeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedGroupRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllocatedGroupRangeDetailComponent,
    resolve: {
      allocatedGroupRange: AllocatedGroupRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedGroupRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllocatedGroupRangeUpdateComponent,
    resolve: {
      allocatedGroupRange: AllocatedGroupRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedGroupRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllocatedGroupRangeUpdateComponent,
    resolve: {
      allocatedGroupRange: AllocatedGroupRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedGroupRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allocatedGroupRangePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllocatedGroupRangeDeletePopupComponent,
    resolve: {
      allocatedGroupRange: AllocatedGroupRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedGroupRange.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
