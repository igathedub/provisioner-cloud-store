import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { RetransmitComponent } from './retransmit.component';
import { RetransmitDetailComponent } from './retransmit-detail.component';
import { RetransmitUpdateComponent } from './retransmit-update.component';
import { RetransmitDeletePopupComponent, RetransmitDeleteDialogComponent } from './retransmit-delete-dialog.component';
import { retransmitRoute, retransmitPopupRoute } from './retransmit.route';

const ENTITY_STATES = [...retransmitRoute, ...retransmitPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RetransmitComponent,
    RetransmitDetailComponent,
    RetransmitUpdateComponent,
    RetransmitDeleteDialogComponent,
    RetransmitDeletePopupComponent
  ],
  entryComponents: [RetransmitDeleteDialogComponent]
})
export class ProvisionercloudRetransmitModule {}
