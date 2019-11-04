import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.ProvisionercloudCustomerModule)
      },
      {
        path: 'app-user',
        loadChildren: () => import('./app-user/app-user.module').then(m => m.ProvisionercloudAppUserModule)
      },
      {
        path: 'facility',
        loadChildren: () => import('./facility/facility.module').then(m => m.ProvisionercloudFacilityModule)
      },
      {
        path: 'role',
        loadChildren: () => import('./role/role.module').then(m => m.ProvisionercloudRoleModule)
      },
      {
        path: 'mesh-group',
        loadChildren: () => import('./mesh-group/mesh-group.module').then(m => m.ProvisionercloudMeshGroupModule)
      },
      {
        path: 'app-key',
        loadChildren: () => import('./app-key/app-key.module').then(m => m.ProvisionercloudAppKeyModule)
      },
      {
        path: 'net-key',
        loadChildren: () => import('./net-key/net-key.module').then(m => m.ProvisionercloudNetKeyModule)
      },
      {
        path: 'key-index',
        loadChildren: () => import('./key-index/key-index.module').then(m => m.ProvisionercloudKeyIndexModule)
      },
      {
        path: 'features',
        loadChildren: () => import('./features/features.module').then(m => m.ProvisionercloudFeaturesModule)
      },
      {
        path: 'retransmit',
        loadChildren: () => import('./retransmit/retransmit.module').then(m => m.ProvisionercloudRetransmitModule)
      },
      {
        path: 'publish',
        loadChildren: () => import('./publish/publish.module').then(m => m.ProvisionercloudPublishModule)
      },
      {
        path: 'model',
        loadChildren: () => import('./model/model.module').then(m => m.ProvisionercloudModelModule)
      },
      {
        path: 'element',
        loadChildren: () => import('./element/element.module').then(m => m.ProvisionercloudElementModule)
      },
      {
        path: 'allocated-range',
        loadChildren: () => import('./allocated-range/allocated-range.module').then(m => m.ProvisionercloudAllocatedRangeModule)
      },
      {
        path: 'node',
        loadChildren: () => import('./node/node.module').then(m => m.ProvisionercloudNodeModule)
      },
      {
        path: 'provisioner',
        loadChildren: () => import('./provisioner/provisioner.module').then(m => m.ProvisionercloudProvisionerModule)
      },
      {
        path: 'network-configuration',
        loadChildren: () =>
          import('./network-configuration/network-configuration.module').then(m => m.ProvisionercloudNetworkConfigurationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ProvisionercloudEntityModule {}
