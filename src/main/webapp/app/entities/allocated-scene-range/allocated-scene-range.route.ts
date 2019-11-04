import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';
import { AllocatedSceneRangeService } from './allocated-scene-range.service';
import { AllocatedSceneRangeComponent } from './allocated-scene-range.component';
import { AllocatedSceneRangeDetailComponent } from './allocated-scene-range-detail.component';
import { AllocatedSceneRangeUpdateComponent } from './allocated-scene-range-update.component';
import { AllocatedSceneRangeDeletePopupComponent } from './allocated-scene-range-delete-dialog.component';
import { IAllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';

@Injectable({ providedIn: 'root' })
export class AllocatedSceneRangeResolve implements Resolve<IAllocatedSceneRange> {
  constructor(private service: AllocatedSceneRangeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllocatedSceneRange> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AllocatedSceneRange>) => response.ok),
        map((allocatedSceneRange: HttpResponse<AllocatedSceneRange>) => allocatedSceneRange.body)
      );
    }
    return of(new AllocatedSceneRange());
  }
}

export const allocatedSceneRangeRoute: Routes = [
  {
    path: '',
    component: AllocatedSceneRangeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedSceneRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllocatedSceneRangeDetailComponent,
    resolve: {
      allocatedSceneRange: AllocatedSceneRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedSceneRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllocatedSceneRangeUpdateComponent,
    resolve: {
      allocatedSceneRange: AllocatedSceneRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedSceneRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllocatedSceneRangeUpdateComponent,
    resolve: {
      allocatedSceneRange: AllocatedSceneRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedSceneRange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allocatedSceneRangePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllocatedSceneRangeDeletePopupComponent,
    resolve: {
      allocatedSceneRange: AllocatedSceneRangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.allocatedSceneRange.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
