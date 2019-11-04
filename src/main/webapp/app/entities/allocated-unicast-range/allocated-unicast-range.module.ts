import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { AllocatedUnicastRangeComponent } from './allocated-unicast-range.component';
import { AllocatedUnicastRangeDetailComponent } from './allocated-unicast-range-detail.component';
import { AllocatedUnicastRangeUpdateComponent } from './allocated-unicast-range-update.component';
import {
  AllocatedUnicastRangeDeletePopupComponent,
  AllocatedUnicastRangeDeleteDialogComponent
} from './allocated-unicast-range-delete-dialog.component';
import { allocatedUnicastRangeRoute, allocatedUnicastRangePopupRoute } from './allocated-unicast-range.route';

const ENTITY_STATES = [...allocatedUnicastRangeRoute, ...allocatedUnicastRangePopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllocatedUnicastRangeComponent,
    AllocatedUnicastRangeDetailComponent,
    AllocatedUnicastRangeUpdateComponent,
    AllocatedUnicastRangeDeleteDialogComponent,
    AllocatedUnicastRangeDeletePopupComponent
  ],
  entryComponents: [AllocatedUnicastRangeDeleteDialogComponent]
})
export class ProvisionercloudAllocatedUnicastRangeModule {}
