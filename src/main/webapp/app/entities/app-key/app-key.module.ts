import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { AppKeyComponent } from './app-key.component';
import { AppKeyDetailComponent } from './app-key-detail.component';
import { AppKeyUpdateComponent } from './app-key-update.component';
import { AppKeyDeletePopupComponent, AppKeyDeleteDialogComponent } from './app-key-delete-dialog.component';
import { appKeyRoute, appKeyPopupRoute } from './app-key.route';

const ENTITY_STATES = [...appKeyRoute, ...appKeyPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AppKeyComponent, AppKeyDetailComponent, AppKeyUpdateComponent, AppKeyDeleteDialogComponent, AppKeyDeletePopupComponent],
  entryComponents: [AppKeyDeleteDialogComponent]
})
export class ProvisionercloudAppKeyModule {}
