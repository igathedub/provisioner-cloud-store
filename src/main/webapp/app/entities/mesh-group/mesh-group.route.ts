import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MeshGroup } from 'app/shared/model/mesh-group.model';
import { MeshGroupService } from './mesh-group.service';
import { MeshGroupComponent } from './mesh-group.component';
import { MeshGroupDetailComponent } from './mesh-group-detail.component';
import { MeshGroupUpdateComponent } from './mesh-group-update.component';
import { MeshGroupDeletePopupComponent } from './mesh-group-delete-dialog.component';
import { IMeshGroup } from 'app/shared/model/mesh-group.model';

@Injectable({ providedIn: 'root' })
export class MeshGroupResolve implements Resolve<IMeshGroup> {
  constructor(private service: MeshGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMeshGroup> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MeshGroup>) => response.ok),
        map((meshGroup: HttpResponse<MeshGroup>) => meshGroup.body)
      );
    }
    return of(new MeshGroup());
  }
}

export const meshGroupRoute: Routes = [
  {
    path: '',
    component: MeshGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.meshGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeshGroupDetailComponent,
    resolve: {
      meshGroup: MeshGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.meshGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeshGroupUpdateComponent,
    resolve: {
      meshGroup: MeshGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.meshGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeshGroupUpdateComponent,
    resolve: {
      meshGroup: MeshGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.meshGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const meshGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MeshGroupDeletePopupComponent,
    resolve: {
      meshGroup: MeshGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.meshGroup.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
