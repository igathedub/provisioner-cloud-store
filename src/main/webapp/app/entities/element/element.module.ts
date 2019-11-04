import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { ElementComponent } from './element.component';
import { ElementDetailComponent } from './element-detail.component';
import { ElementUpdateComponent } from './element-update.component';
import { ElementDeletePopupComponent, ElementDeleteDialogComponent } from './element-delete-dialog.component';
import { elementRoute, elementPopupRoute } from './element.route';

const ENTITY_STATES = [...elementRoute, ...elementPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ElementComponent,
    ElementDetailComponent,
    ElementUpdateComponent,
    ElementDeleteDialogComponent,
    ElementDeletePopupComponent
  ],
  entryComponents: [ElementDeleteDialogComponent]
})
export class ProvisionercloudElementModule {}
