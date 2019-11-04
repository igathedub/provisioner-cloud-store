import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { NetKeyIndexComponent } from './net-key-index.component';
import { NetKeyIndexDetailComponent } from './net-key-index-detail.component';
import { NetKeyIndexUpdateComponent } from './net-key-index-update.component';
import { NetKeyIndexDeletePopupComponent, NetKeyIndexDeleteDialogComponent } from './net-key-index-delete-dialog.component';
import { netKeyIndexRoute, netKeyIndexPopupRoute } from './net-key-index.route';

const ENTITY_STATES = [...netKeyIndexRoute, ...netKeyIndexPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NetKeyIndexComponent,
    NetKeyIndexDetailComponent,
    NetKeyIndexUpdateComponent,
    NetKeyIndexDeleteDialogComponent,
    NetKeyIndexDeletePopupComponent
  ],
  entryComponents: [NetKeyIndexDeleteDialogComponent]
})
export class ProvisionercloudNetKeyIndexModule {}
