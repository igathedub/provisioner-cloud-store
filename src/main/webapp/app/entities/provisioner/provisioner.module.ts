import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { ProvisionerComponent } from './provisioner.component';
import { ProvisionerDetailComponent } from './provisioner-detail.component';
import { ProvisionerUpdateComponent } from './provisioner-update.component';
import { ProvisionerDeletePopupComponent, ProvisionerDeleteDialogComponent } from './provisioner-delete-dialog.component';
import { provisionerRoute, provisionerPopupRoute } from './provisioner.route';

const ENTITY_STATES = [...provisionerRoute, ...provisionerPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProvisionerComponent,
    ProvisionerDetailComponent,
    ProvisionerUpdateComponent,
    ProvisionerDeleteDialogComponent,
    ProvisionerDeletePopupComponent
  ],
  entryComponents: [ProvisionerDeleteDialogComponent]
})
export class ProvisionercloudProvisionerModule {}
