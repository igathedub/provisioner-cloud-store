import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Facility } from 'app/shared/model/facility.model';
import { FacilityService } from './facility.service';
import { FacilityComponent } from './facility.component';
import { FacilityDetailComponent } from './facility-detail.component';
import { FacilityUpdateComponent } from './facility-update.component';
import { FacilityDeletePopupComponent } from './facility-delete-dialog.component';
import { IFacility } from 'app/shared/model/facility.model';

@Injectable({ providedIn: 'root' })
export class FacilityResolve implements Resolve<IFacility> {
  constructor(private service: FacilityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFacility> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Facility>) => response.ok),
        map((facility: HttpResponse<Facility>) => facility.body)
      );
    }
    return of(new Facility());
  }
}

export const facilityRoute: Routes = [
  {
    path: '',
    component: FacilityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.facility.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FacilityDetailComponent,
    resolve: {
      facility: FacilityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.facility.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FacilityUpdateComponent,
    resolve: {
      facility: FacilityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.facility.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FacilityUpdateComponent,
    resolve: {
      facility: FacilityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.facility.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const facilityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FacilityDeletePopupComponent,
    resolve: {
      facility: FacilityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.facility.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
