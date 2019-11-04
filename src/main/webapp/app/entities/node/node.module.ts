import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { NodeComponent } from './node.component';
import { NodeDetailComponent } from './node-detail.component';
import { NodeUpdateComponent } from './node-update.component';
import { NodeDeletePopupComponent, NodeDeleteDialogComponent } from './node-delete-dialog.component';
import { nodeRoute, nodePopupRoute } from './node.route';

const ENTITY_STATES = [...nodeRoute, ...nodePopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NodeComponent, NodeDetailComponent, NodeUpdateComponent, NodeDeleteDialogComponent, NodeDeletePopupComponent],
  entryComponents: [NodeDeleteDialogComponent]
})
export class ProvisionercloudNodeModule {}
