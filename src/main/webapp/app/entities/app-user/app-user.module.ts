import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { AppUserComponent } from './app-user.component';
import { AppUserDetailComponent } from './app-user-detail.component';
import { AppUserUpdateComponent } from './app-user-update.component';
import { AppUserDeletePopupComponent, AppUserDeleteDialogComponent } from './app-user-delete-dialog.component';
import { appUserRoute, appUserPopupRoute } from './app-user.route';

const ENTITY_STATES = [...appUserRoute, ...appUserPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AppUserComponent,
    AppUserDetailComponent,
    AppUserUpdateComponent,
    AppUserDeleteDialogComponent,
    AppUserDeletePopupComponent
  ],
  entryComponents: [AppUserDeleteDialogComponent]
})
export class ProvisionercloudAppUserModule {}
