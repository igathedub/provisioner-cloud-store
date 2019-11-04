import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProvisionercloudSharedModule } from 'app/shared/shared.module';
import { MeshGroupComponent } from './mesh-group.component';
import { MeshGroupDetailComponent } from './mesh-group-detail.component';
import { MeshGroupUpdateComponent } from './mesh-group-update.component';
import { MeshGroupDeletePopupComponent, MeshGroupDeleteDialogComponent } from './mesh-group-delete-dialog.component';
import { meshGroupRoute, meshGroupPopupRoute } from './mesh-group.route';

const ENTITY_STATES = [...meshGroupRoute, ...meshGroupPopupRoute];

@NgModule({
  imports: [ProvisionercloudSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MeshGroupComponent,
    MeshGroupDetailComponent,
    MeshGroupUpdateComponent,
    MeshGroupDeleteDialogComponent,
    MeshGroupDeletePopupComponent
  ],
  entryComponents: [MeshGroupDeleteDialogComponent]
})
export class ProvisionercloudMeshGroupModule {}
