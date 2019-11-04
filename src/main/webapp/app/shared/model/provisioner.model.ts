import { IAllocatedRange } from 'app/shared/model/allocated-range.model';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface IProvisioner {
  id?: number;
  uUID?: string;
  provisionerName?: string;
  aallocatedGroupRanges?: IAllocatedRange[];
  aallocatedUnicastRanges?: IAllocatedRange[];
  aallocatedSceneRanges?: IAllocatedRange[];
  networkConfiguration?: INetworkConfiguration;
}

export class Provisioner implements IProvisioner {
  constructor(
    public id?: number,
    public uUID?: string,
    public provisionerName?: string,
    public aallocatedGroupRanges?: IAllocatedRange[],
    public aallocatedUnicastRanges?: IAllocatedRange[],
    public aallocatedSceneRanges?: IAllocatedRange[],
    public networkConfiguration?: INetworkConfiguration
  ) {}
}
