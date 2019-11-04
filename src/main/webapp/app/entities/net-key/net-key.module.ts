import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { NetKeyComponent } from './net-key.component';
import { NetKeyDetailComponent } from './net-key-detail.component';
import { NetKeyUpdateComponent } from './net-key-update.component';
import { NetKeyDeletePopupComponent, NetKeyDeleteDialogComponent } from './net-key-delete-dialog.component';
import { netKeyRoute, netKeyPopupRoute } from './net-key.route';

const ENTITY_STATES = [...netKeyRoute, ...netKeyPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NetKeyComponent, NetKeyDetailComponent, NetKeyUpdateComponent, NetKeyDeleteDialogComponent, NetKeyDeletePopupComponent],
  entryComponents: [NetKeyDeleteDialogComponent]
})
export class ProvisionercloudNetKeyModule {}
