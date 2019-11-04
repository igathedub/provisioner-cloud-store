import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';
import { AllocatedUnicastRangeService } from './allocated-unicast-range.service';
import { AllocatedUnicastRangeComponent } from './allocated-unicast-range.component';
import { AllocatedUnicastRangeDetailComponent } from './allocated-unicast-range-detail.component';
import { AllocatedUnicastRangeUpdateComponent } from './allocated-unicast-range-update.component';
import { AllocatedUnicastRangeDeletePopupComponent } from './allocated-unicast-range-delete-dialog.component';
import { IAllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';

@Injectable({ providedIn: 'root' })
export class AllocatedUnicastRangeResolve implements Resolve<IAllocatedUnicastRange> {
  constructor(private service: AllocatedUnicastRangeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllocatedUnicastRange> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AllocatedUnicastRange>) => response.ok),
        map((allocatedUnicastRange: HttpResponse<AllocatedUnicastRange>) => allocatedUnicastRange.body)
      );
    }
    return of(new AllocatedUnicastRange());
  }
}

export const allocatedUnicastRangeRoute: Routes = [
  {
    path: '',
    component: AllocatedUnicastRangeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedUnicastRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllocatedUnicastRangeDetailComponent,
    resolve: {
      allocatedUnicastRange: AllocatedUnicastRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedUnicastRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllocatedUnicastRangeUpdateComponent,
    resolve: {
      allocatedUnicastRange: AllocatedUnicastRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedUnicastRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllocatedUnicastRangeUpdateComponent,
    resolve: {
      allocatedUnicastRange: AllocatedUnicastRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedUnicastRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allocatedUnicastRangePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllocatedUnicastRangeDeletePopupComponent,
    resolve: {
      allocatedUnicastRange: AllocatedUnicastRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedUnicastRange.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
