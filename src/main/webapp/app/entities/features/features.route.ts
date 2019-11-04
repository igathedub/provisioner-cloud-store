import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Features } from 'app/shared/model/features.model';
import { FeaturesService } from './features.service';
import { FeaturesComponent } from './features.component';
import { FeaturesDetailComponent } from './features-detail.component';
import { FeaturesUpdateComponent } from './features-update.component';
import { FeaturesDeletePopupComponent } from './features-delete-dialog.component';
import { IFeatures } from 'app/shared/model/features.model';

@Injectable({ providedIn: 'root' })
export class FeaturesResolve implements Resolve<IFeatures> {
  constructor(private service: FeaturesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFeatures> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Features>) => response.ok),
        map((features: HttpResponse<Features>) => features.body)
      );
    }
    return of(new Features());
  }
}

export const featuresRoute: Routes = [
  {
    path: '',
    component: FeaturesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.features.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FeaturesDetailComponent,
    resolve: {
      features: FeaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.features.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FeaturesUpdateComponent,
    resolve: {
      features: FeaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.features.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FeaturesUpdateComponent,
    resolve: {
      features: FeaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.features.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const featuresPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FeaturesDeletePopupComponent,
    resolve: {
      features: FeaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.features.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
