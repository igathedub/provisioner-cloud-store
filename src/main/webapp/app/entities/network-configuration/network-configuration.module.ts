import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { NetworkConfigurationComponent } from './network-configuration.component';
import { NetworkConfigurationDetailComponent } from './network-configuration-detail.component';
import { NetworkConfigurationUpdateComponent } from './network-configuration-update.component';
import {
  NetworkConfigurationDeletePopupComponent,
  NetworkConfigurationDeleteDialogComponent
} from './network-configuration-delete-dialog.component';
import { networkConfigurationRoute, networkConfigurationPopupRoute } from './network-configuration.route';

const ENTITY_STATES = [...networkConfigurationRoute, ...networkConfigurationPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NetworkConfigurationComponent,
    NetworkConfigurationDetailComponent,
    NetworkConfigurationUpdateComponent,
    NetworkConfigurationDeleteDialogComponent,
    NetworkConfigurationDeletePopupComponent
  ],
  entryComponents: [NetworkConfigurationDeleteDialogComponent]
})
export class ProvisionercloudNetworkConfigurationModule {}
