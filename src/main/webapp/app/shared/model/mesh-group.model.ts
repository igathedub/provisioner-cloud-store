import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface IMeshGroup {
  id?: number;
  name?: string;
  address?: string;
  parentAddress?: string;
  networkConfiguration?: INetworkConfiguration;
}

export class MeshGroup implements IMeshGroup {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public parentAddress?: string,
    public networkConfiguration?: INetworkConfiguration
  ) {}
}
