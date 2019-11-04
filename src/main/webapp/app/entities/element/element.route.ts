import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Element } from 'app/shared/model/element.model';
import { ElementService } from './element.service';
import { ElementComponent } from './element.component';
import { ElementDetailComponent } from './element-detail.component';
import { ElementUpdateComponent } from './element-update.component';
import { ElementDeletePopupComponent } from './element-delete-dialog.component';
import { IElement } from 'app/shared/model/element.model';

@Injectable({ providedIn: 'root' })
export class ElementResolve implements Resolve<IElement> {
  constructor(private service: ElementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IElement> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Element>) => response.ok),
        map((element: HttpResponse<Element>) => element.body)
      );
    }
    return of(new Element());
  }
}

export const elementRoute: Routes = [
  {
    path: '',
    component: ElementComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.element.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ElementDetailComponent,
    resolve: {
      element: ElementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.element.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ElementUpdateComponent,
    resolve: {
      element: ElementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.element.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ElementUpdateComponent,
    resolve: {
      element: ElementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.element.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const elementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ElementDeletePopupComponent,
    resolve: {
      element: ElementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.element.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
