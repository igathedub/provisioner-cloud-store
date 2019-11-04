import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { PublishComponent } from './publish.component';
import { PublishDetailComponent } from './publish-detail.component';
import { PublishUpdateComponent } from './publish-update.component';
import { PublishDeletePopupComponent, PublishDeleteDialogComponent } from './publish-delete-dialog.component';
import { publishRoute, publishPopupRoute } from './publish.route';

const ENTITY_STATES = [...publishRoute, ...publishPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PublishComponent,
    PublishDetailComponent,
    PublishUpdateComponent,
    PublishDeleteDialogComponent,
    PublishDeletePopupComponent
  ],
  entryComponents: [PublishDeleteDialogComponent]
})
export class ProvisionercloudPublishModule {}
