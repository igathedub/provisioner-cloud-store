import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { KeyIndex } from 'app/shared/model/key-index.model';
import { KeyIndexService } from './key-index.service';
import { KeyIndexComponent } from './key-index.component';
import { KeyIndexDetailComponent } from './key-index-detail.component';
import { KeyIndexUpdateComponent } from './key-index-update.component';
import { KeyIndexDeletePopupComponent } from './key-index-delete-dialog.component';
import { IKeyIndex } from 'app/shared/model/key-index.model';

@Injectable({ providedIn: 'root' })
export class KeyIndexResolve implements Resolve<IKeyIndex> {
  constructor(private service: KeyIndexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IKeyIndex> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<KeyIndex>) => response.ok),
        map((keyIndex: HttpResponse<KeyIndex>) => keyIndex.body)
      );
    }
    return of(new KeyIndex());
  }
}

export const keyIndexRoute: Routes = [
  {
    path: '',
    component: KeyIndexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.keyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: KeyIndexDetailComponent,
    resolve: {
      keyIndex: KeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.keyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: KeyIndexUpdateComponent,
    resolve: {
      keyIndex: KeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.keyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: KeyIndexUpdateComponent,
    resolve: {
      keyIndex: KeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.keyIndex.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const keyIndexPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: KeyIndexDeletePopupComponent,
    resolve: {
      keyIndex: KeyIndexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.keyIndex.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
