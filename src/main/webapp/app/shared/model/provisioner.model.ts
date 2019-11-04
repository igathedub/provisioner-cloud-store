import { IAllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';
import { IAllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';
import { IAllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface IProvisioner {
  id?: number;
  uUID?: string;
  provisionerName?: string;
  aallocatedGroupRanges?: IAllocatedGroupRange[];
  aallocatedUnicastRanges?: IAllocatedUnicastRange[];
  aallocatedSceneRanges?: IAllocatedSceneRange[];
  networkConfiguration?: INetworkConfiguration;
}

export class Provisioner implements IProvisioner {
  constructor(
    public id?: number,
    public uUID?: string,
    public provisionerName?: string,
    public aallocatedGroupRanges?: IAllocatedGroupRange[],
    public aallocatedUnicastRanges?: IAllocatedUnicastRange[],
    public aallocatedSceneRanges?: IAllocatedSceneRange[],
    public networkConfiguration?: INetworkConfiguration
  ) {}
}
