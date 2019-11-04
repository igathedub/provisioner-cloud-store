import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { AllocatedRangeComponent } from './allocated-range.component';
import { AllocatedRangeDetailComponent } from './allocated-range-detail.component';
import { AllocatedRangeUpdateComponent } from './allocated-range-update.component';
import { AllocatedRangeDeletePopupComponent, AllocatedRangeDeleteDialogComponent } from './allocated-range-delete-dialog.component';
import { allocatedRangeRoute, allocatedRangePopupRoute } from './allocated-range.route';

const ENTITY_STATES = [...allocatedRangeRoute, ...allocatedRangePopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllocatedRangeComponent,
    AllocatedRangeDetailComponent,
    AllocatedRangeUpdateComponent,
    AllocatedRangeDeleteDialogComponent,
    AllocatedRangeDeletePopupComponent
  ],
  entryComponents: [AllocatedRangeDeleteDialogComponent]
})
export class ProvisionercloudAllocatedRangeModule {}
