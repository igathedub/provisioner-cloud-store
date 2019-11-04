import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface INetKey {
  id?: number;
  phase?: number;
  minSecurity?: string;
  key?: string;
  timestamp?: string;
  name?: string;
  index?: number;
  networkConfiguration?: INetworkConfiguration;
}

export class NetKey implements INetKey {
  constructor(
    public id?: number,
    public phase?: number,
    public minSecurity?: string,
    public key?: string,
    public timestamp?: string,
    public name?: string,
    public index?: number,
    public networkConfiguration?: INetworkConfiguration
  ) {}
}
