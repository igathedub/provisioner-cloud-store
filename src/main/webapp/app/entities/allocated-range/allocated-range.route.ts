import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllocatedRange } from 'app/shared/model/allocated-range.model';
import { AllocatedRangeService } from './allocated-range.service';
import { AllocatedRangeComponent } from './allocated-range.component';
import { AllocatedRangeDetailComponent } from './allocated-range-detail.component';
import { AllocatedRangeUpdateComponent } from './allocated-range-update.component';
import { AllocatedRangeDeletePopupComponent } from './allocated-range-delete-dialog.component';
import { IAllocatedRange } from 'app/shared/model/allocated-range.model';

@Injectable({ providedIn: 'root' })
export class AllocatedRangeResolve implements Resolve<IAllocatedRange> {
  constructor(private service: AllocatedRangeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllocatedRange> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AllocatedRange>) => response.ok),
        map((allocatedRange: HttpResponse<AllocatedRange>) => allocatedRange.body)
      );
    }
    return of(new AllocatedRange());
  }
}

export const allocatedRangeRoute: Routes = [
  {
    path: '',
    component: AllocatedRangeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllocatedRangeDetailComponent,
    resolve: {
      allocatedRange: AllocatedRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllocatedRangeUpdateComponent,
    resolve: {
      allocatedRange: AllocatedRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllocatedRangeUpdateComponent,
    resolve: {
      allocatedRange: AllocatedRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allocatedRangePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllocatedRangeDeletePopupComponent,
    resolve: {
      allocatedRange: AllocatedRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedRange.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
