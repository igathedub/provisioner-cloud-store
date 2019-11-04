import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from './network-configuration.service';
import { NetworkConfigurationComponent } from './network-configuration.component';
import { NetworkConfigurationDetailComponent } from './network-configuration-detail.component';
import { NetworkConfigurationUpdateComponent } from './network-configuration-update.component';
import { NetworkConfigurationDeletePopupComponent } from './network-configuration-delete-dialog.component';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

@Injectable({ providedIn: 'root' })
export class NetworkConfigurationResolve implements Resolve<INetworkConfiguration> {
  constructor(private service: NetworkConfigurationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INetworkConfiguration> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NetworkConfiguration>) => response.ok),
        map((networkConfiguration: HttpResponse<NetworkConfiguration>) => networkConfiguration.body)
      );
    }
    return of(new NetworkConfiguration());
  }
}

export const networkConfigurationRoute: Routes = [
  {
    path: '',
    component: NetworkConfigurationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.networkConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NetworkConfigurationDetailComponent,
    resolve: {
      networkConfiguration: NetworkConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.networkConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NetworkConfigurationUpdateComponent,
    resolve: {
      networkConfiguration: NetworkConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.networkConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NetworkConfigurationUpdateComponent,
    resolve: {
      networkConfiguration: NetworkConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.networkConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const networkConfigurationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NetworkConfigurationDeletePopupComponent,
    resolve: {
      networkConfiguration: NetworkConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'provisionercloudApp.networkConfiguration.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
