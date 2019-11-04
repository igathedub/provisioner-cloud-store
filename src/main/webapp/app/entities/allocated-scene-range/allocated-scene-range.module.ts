import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { AllocatedSceneRangeComponent } from './allocated-scene-range.component';
import { AllocatedSceneRangeDetailComponent } from './allocated-scene-range-detail.component';
import { AllocatedSceneRangeUpdateComponent } from './allocated-scene-range-update.component';
import {
  AllocatedSceneRangeDeletePopupComponent,
  AllocatedSceneRangeDeleteDialogComponent
} from './allocated-scene-range-delete-dialog.component';
import { allocatedSceneRangeRoute, allocatedSceneRangePopupRoute } from './allocated-scene-range.route';

const ENTITY_STATES = [...allocatedSceneRangeRoute, ...allocatedSceneRangePopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllocatedSceneRangeComponent,
    AllocatedSceneRangeDetailComponent,
    AllocatedSceneRangeUpdateComponent,
    AllocatedSceneRangeDeleteDialogComponent,
    AllocatedSceneRangeDeletePopupComponent
  ],
  entryComponents: [AllocatedSceneRangeDeleteDialogComponent]
})
export class ProvisionercloudAllocatedSceneRangeModule {}
