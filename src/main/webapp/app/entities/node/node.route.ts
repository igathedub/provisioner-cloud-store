import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Node } from 'app/shared/model/node.model';
import { NodeService } from './node.service';
import { NodeComponent } from './node.component';
import { NodeDetailComponent } from './node-detail.component';
import { NodeUpdateComponent } from './node-update.component';
import { NodeDeletePopupComponent } from './node-delete-dialog.component';
import { INode } from 'app/shared/model/node.model';

@Injectable({ providedIn: 'root' })
export class NodeResolve implements Resolve<INode> {
  constructor(private service: NodeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INode> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Node>) => response.ok),
        map((node: HttpResponse<Node>) => node.body)
      );
    }
    return of(new Node());
  }
}

export const nodeRoute: Routes = [
  {
    path: '',
    component: NodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.node.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NodeDetailComponent,
    resolve: {
      node: NodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.node.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NodeUpdateComponent,
    resolve: {
      node: NodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.node.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NodeUpdateComponent,
    resolve: {
      node: NodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.node.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nodePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NodeDeletePopupComponent,
    resolve: {
      node: NodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.node.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
