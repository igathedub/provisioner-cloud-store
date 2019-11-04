import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { FeaturesComponent } from './features.component';
import { FeaturesDetailComponent } from './features-detail.component';
import { FeaturesUpdateComponent } from './features-update.component';
import { FeaturesDeletePopupComponent, FeaturesDeleteDialogComponent } from './features-delete-dialog.component';
import { featuresRoute, featuresPopupRoute } from './features.route';

const ENTITY_STATES = [...featuresRoute, ...featuresPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FeaturesComponent,
    FeaturesDetailComponent,
    FeaturesUpdateComponent,
    FeaturesDeleteDialogComponent,
    FeaturesDeletePopupComponent
  ],
  entryComponents: [FeaturesDeleteDialogComponent]
})
export class ProvisionercloudFeaturesModule {}
