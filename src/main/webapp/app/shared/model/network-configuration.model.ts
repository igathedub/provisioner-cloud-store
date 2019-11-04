import { IFacility } from 'app/shared/model/facility.model';
import { IProvisioner } from 'app/shared/model/provisioner.model';
import { INode } from 'app/shared/model/node.model';
import { IMeshGroup } from 'app/shared/model/mesh-group.model';
import { INetKey } from 'app/shared/model/net-key.model';
import { IAppKey } from 'app/shared/model/app-key.model';

export interface INetworkConfiguration {
  id?: number;
  meshUUID?: string;
  timestamp?: string;
  meshName?: number;
  facility?: IFacility;
  provisioners?: IProvisioner[];
  nodes?: INode[];
  groups?: IMeshGroup[];
  netKeys?: INetKey[];
  appKeys?: IAppKey[];
}

export class NetworkConfiguration implements INetworkConfiguration {
  constructor(
    public id?: number,
    public meshUUID?: string,
    public timestamp?: string,
    public meshName?: number,
    public facility?: IFacility,
    public provisioners?: IProvisioner[],
    public nodes?: INode[],
    public groups?: IMeshGroup[],
    public netKeys?: INetKey[],
    public appKeys?: IAppKey[]
  ) {}
}
