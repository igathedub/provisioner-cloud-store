import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { FacilityComponent } from './facility.component';
import { FacilityDetailComponent } from './facility-detail.component';
import { FacilityUpdateComponent } from './facility-update.component';
import { FacilityDeletePopupComponent, FacilityDeleteDialogComponent } from './facility-delete-dialog.component';
import { facilityRoute, facilityPopupRoute } from './facility.route';

const ENTITY_STATES = [...facilityRoute, ...facilityPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FacilityComponent,
    FacilityDetailComponent,
    FacilityUpdateComponent,
    FacilityDeleteDialogComponent,
    FacilityDeletePopupComponent
  ],
  entryComponents: [FacilityDeleteDialogComponent]
})
export class ProvisionercloudFacilityModule {}
