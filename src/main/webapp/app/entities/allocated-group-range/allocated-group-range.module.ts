import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { AllocatedGroupRangeComponent } from './allocated-group-range.component';
import { AllocatedGroupRangeDetailComponent } from './allocated-group-range-detail.component';
import { AllocatedGroupRangeUpdateComponent } from './allocated-group-range-update.component';
import {
  AllocatedGroupRangeDeletePopupComponent,
  AllocatedGroupRangeDeleteDialogComponent
} from './allocated-group-range-delete-dialog.component';
import { allocatedGroupRangeRoute, allocatedGroupRangePopupRoute } from './allocated-group-range.route';

const ENTITY_STATES = [...allocatedGroupRangeRoute, ...allocatedGroupRangePopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllocatedGroupRangeComponent,
    AllocatedGroupRangeDetailComponent,
    AllocatedGroupRangeUpdateComponent,
    AllocatedGroupRangeDeleteDialogComponent,
    AllocatedGroupRangeDeletePopupComponent
  ],
  entryComponents: [AllocatedGroupRangeDeleteDialogComponent]
})
export class ProvisionercloudAllocatedGroupRangeModule {}
