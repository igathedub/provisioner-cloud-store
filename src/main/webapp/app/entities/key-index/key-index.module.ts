import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { KeyIndexComponent } from './key-index.component';
import { KeyIndexDetailComponent } from './key-index-detail.component';
import { KeyIndexUpdateComponent } from './key-index-update.component';
import { KeyIndexDeletePopupComponent, KeyIndexDeleteDialogComponent } from './key-index-delete-dialog.component';
import { keyIndexRoute, keyIndexPopupRoute } from './key-index.route';

const ENTITY_STATES = [...keyIndexRoute, ...keyIndexPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    KeyIndexComponent,
    KeyIndexDetailComponent,
    KeyIndexUpdateComponent,
    KeyIndexDeleteDialogComponent,
    KeyIndexDeletePopupComponent
  ],
  entryComponents: [KeyIndexDeleteDialogComponent]
})
export class ProvisionercloudKeyIndexModule {}
